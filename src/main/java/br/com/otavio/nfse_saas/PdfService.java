package br.com.otavio.nfse_saas;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {

    private final TemplateEngine templateEngine;
    private final FileStorageService storageService;

    // CONSTRUTOR ÚNICO: O Spring vai injetar as duas dependências aqui
    public PdfService(TemplateEngine templateEngine, FileStorageService storageService) {
        this.templateEngine = templateEngine;
        this.storageService = storageService;
    }

    public byte[] generateInvoicePdf(InvoiceEntity invoice, Company company) {
        try {
            Context context = new Context();
            context.setVariable("company", company);
            context.setVariable("customerName", invoice.getCustomerNameSnapshot());
            context.setVariable("customerTaxId", invoice.getCustomerTaxIdSnapshot());
            context.setVariable("serviceDescription", invoice.getServiceDescriptionSnapshot());
            context.setVariable("totalValue", String.format("%.2f", invoice.getServiceValue()));
            context.setVariable("emissionDate", invoice.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            context.setVariable("invoiceId", invoice.getId());

            String htmlProcessado = templateEngine.process("invoice", context);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlProcessado);
            renderer.layout();
            renderer.createPDF(outputStream);

            byte[] pdfBytes = outputStream.toByteArray();

            // Salva no banco via serviço de storage
            storageService.saveFile(
                    pdfBytes,
                    "NotaFiscal_" + invoice.getId() + ".pdf",
                    "application/pdf",
                    invoice,
                    "PDF"
            );

            return pdfBytes;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar o PDF: " + e.getMessage());
        }
    }
}