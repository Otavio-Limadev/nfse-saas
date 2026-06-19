package br.com.otavio.nfse_saas.controller;

import br.com.otavio.nfse_saas.service.DigitalCertificateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/companies/{companyId}/certificates")
public class DigitalCertificateController {

    private final DigitalCertificateService certificateService;

    public DigitalCertificateController(DigitalCertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<String> upload(
            @PathVariable Long companyId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("password") String password) {

        try {
            String result = certificateService.uploadCertificate(companyId, file, password);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao salvar: " + e.getMessage());
        }
    }
}