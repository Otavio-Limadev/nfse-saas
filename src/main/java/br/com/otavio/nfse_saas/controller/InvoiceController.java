package br.com.otavio.nfse_saas.controller;

import br.com.otavio.nfse_saas.InvoiceEntity;
import br.com.otavio.nfse_saas.PdfService;
import br.com.otavio.nfse_saas.dto.InvoiceRequestDTO;
import br.com.otavio.nfse_saas.dto.InvoiceSummaryDTO;
import br.com.otavio.nfse_saas.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies/{companyId}/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final PdfService pdfService;

    @PostMapping
    public ResponseEntity<String> createInvoice(@PathVariable Long companyId, @RequestBody InvoiceRequestDTO dto) throws Exception {
        String xmlAssinado = invoiceService.processarEmissaoNota(companyId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(xmlAssinado);
    }

    @PostMapping("/{id}/duplicar")
    public ResponseEntity<InvoiceEntity> duplicar(@PathVariable Long companyId, @PathVariable Long id) {
        InvoiceEntity notaDuplicada = invoiceService.duplicarNota(companyId, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(notaDuplicada);
    }

    @GetMapping
    public ResponseEntity<List<InvoiceSummaryDTO>> listarNotas(@PathVariable Long companyId) {
        return ResponseEntity.ok(invoiceService.listarNotasPorEmpresa(companyId));
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long companyId, @PathVariable Long id) {
        InvoiceEntity invoice = invoiceService.buscarPorId(companyId, id);
        byte[] pdfBytes = pdfService.generateInvoicePdf(invoice, invoice.getCompany());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=NotaFiscal_" + id + ".pdf")
                .body(pdfBytes);
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<String> cancelarNota(@PathVariable Long companyId, @PathVariable Long id) {
        invoiceService.cancelarNota(companyId, id);
        return ResponseEntity.ok("Nota fiscal " + id + " cancelada com sucesso.");
    }
}