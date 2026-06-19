package br.com.otavio.nfse_saas.service;

import br.com.otavio.nfse_saas.*;
import br.com.otavio.nfse_saas.dto.InvoiceRequestDTO;
import br.com.otavio.nfse_saas.dto.InvoiceSummaryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;
    private final ServiceRepository serviceRepository;
    private final InvoiceXmlService invoiceXmlService;
    private final DigitalSignatureService digitalSignatureService;
    private final DigitalCertificateRepository certificateRepository;
    private final PrefeituraIntegrationService prefeituraIntegrationService;
    private final PdfService pdfService;

    @Transactional
    public String processarEmissaoNota(Long companyId, InvoiceRequestDTO dto) throws Exception {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        InvoiceEntity invoice = new InvoiceEntity();
        invoice.setCompany(company);

        Customer customer = customerRepository.findById(dto.getCustomerId())
                .filter(c -> c.getCompany().getId().equals(companyId))
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado ou não pertence à empresa"));

        ServiceEntity service = serviceRepository.findById(dto.getServiceId())
                .filter(s -> s.getCompany().getId().equals(companyId))
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado ou não pertence à empresa"));

        invoice.setCustomer(customer);
        invoice.setServiceItem(service);
        invoice.setCustomerNameSnapshot(customer.getName());
        invoice.setCustomerTaxIdSnapshot(customer.getTaxId());
        invoice.setServiceDescriptionSnapshot(service.getDescription());
        invoice.setServiceValue(service.getValue().doubleValue());
        invoice.setTotalValue(service.getValue().doubleValue());
        invoice.setObservation(dto.getObservation());
        invoice.setStatus(InvoiceStatus.PROCESSING);

        InvoiceEntity savedInvoice = invoiceRepository.save(invoice);

        String xmlBruto = invoiceXmlService.generateXml(savedInvoice);
        DigitalCertificate cert = certificateRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new Exception("Certificado não encontrado!"));

        String xmlAssinado = digitalSignatureService.signXml(xmlBruto, cert);
        boolean sucessoNaPrefeitura = prefeituraIntegrationService.enviarParaPrefeitura(xmlAssinado);

        if (sucessoNaPrefeitura) {
            savedInvoice.setStatus(InvoiceStatus.ISSUED);
            savedInvoice = invoiceRepository.save(savedInvoice);
            try {
                pdfService.generateInvoicePdf(savedInvoice, company);
            } catch (Exception e) {
                System.err.println("Aviso: Falha ao gerar PDF: " + e.getMessage());
            }
        } else {
            savedInvoice.setStatus(InvoiceStatus.ERROR);
            invoiceRepository.save(savedInvoice);
            throw new RuntimeException("Falha na comunicação com a Prefeitura.");
        }
        return xmlAssinado;
    }

    @Transactional
    public InvoiceEntity duplicarNota(Long companyId, Long id) {
        InvoiceEntity notaAntiga = invoiceRepository.findById(id)
                .filter(n -> n.getCompany().getId().equals(companyId))
                .orElseThrow(() -> new RuntimeException("Nota não encontrada para esta empresa"));

        InvoiceEntity novaNota = new InvoiceEntity();
        novaNota.setCompany(notaAntiga.getCompany());
        novaNota.setCustomer(notaAntiga.getCustomer());
        novaNota.setServiceItem(notaAntiga.getServiceItem());
        novaNota.setCustomerNameSnapshot(notaAntiga.getCustomerNameSnapshot());
        novaNota.setCustomerTaxIdSnapshot(notaAntiga.getCustomerTaxIdSnapshot());
        novaNota.setServiceDescriptionSnapshot(notaAntiga.getServiceDescriptionSnapshot());
        novaNota.setServiceValue(notaAntiga.getServiceValue());
        novaNota.setTotalValue(notaAntiga.getTotalValue());
        novaNota.setObservation(notaAntiga.getObservation() + " - (Cópia)");
        novaNota.setStatus(InvoiceStatus.DRAFT);

        return invoiceRepository.save(novaNota);
    }

    @Transactional
    public void cancelarNota(Long companyId, Long id) {
        InvoiceEntity nota = invoiceRepository.findById(id)
                .filter(n -> n.getCompany().getId().equals(companyId))
                .orElseThrow(() -> new RuntimeException("Nota não encontrada para esta empresa"));

        if (nota.getStatus() != InvoiceStatus.ISSUED) {
            throw new RuntimeException("Status inválido para cancelamento: " + nota.getStatus());
        }
        nota.setStatus(InvoiceStatus.CANCELED);
        invoiceRepository.save(nota);
    }

    public InvoiceEntity buscarPorId(Long companyId, Long id) {
        return invoiceRepository.findById(id)
                .filter(n -> n.getCompany().getId().equals(companyId))
                .orElseThrow(() -> new RuntimeException("Nota não encontrada"));
    }

    public List<InvoiceSummaryDTO> listarNotasPorEmpresa(Long companyId) {
        return invoiceRepository.findByCompanyId(companyId).stream().map(nota -> {
            InvoiceSummaryDTO dto = new InvoiceSummaryDTO();
            dto.setInvoiceId(nota.getId());
            dto.setCustomerName(nota.getCustomer().getName());
            dto.setCustomerTaxId(nota.getCustomer().getTaxId());
            dto.setServiceDescription(nota.getServiceItem().getDescription());
            dto.setTotalValue(nota.getTotalValue() != null ? java.math.BigDecimal.valueOf(nota.getTotalValue()) : java.math.BigDecimal.ZERO);
            dto.setStatus(nota.getStatus().name());
            dto.setCreatedAt(nota.getCreatedAt());
            return dto;
        }).toList();
    }
}