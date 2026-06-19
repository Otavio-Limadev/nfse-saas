package br.com.otavio.nfse_saas;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/arquivos")
public class NotaArquivosController {

    private final FileStorageService storageService;
    private final EmailService emailService;
    public NotaArquivosController(FileStorageService storageService, EmailService emailService) {
        this.storageService = storageService;
        this.emailService = emailService;
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> downloadArquivo(@PathVariable Long id) {
        NotaArquivosEntity arquivo = storageService.findById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + arquivo.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(arquivo.getContentType()))
                .body(arquivo.getContent());
    }

    @PostMapping("/{id}/enviar-email")
    public ResponseEntity<String> enviarPorEmail(@PathVariable Long id, @RequestParam String emailDestino) {
        // Pega o arquivo do banco igual no download
        NotaArquivosEntity arquivo = storageService.findById(id);

        // Dispara o e-mail usando EmailService
        emailService.enviarNotaPorEmail(
                emailDestino,
                "Cliente Teste",
                arquivo.getContent(),
                arquivo.getFileName()
        );

        return ResponseEntity.ok("E-mail disparado com sucesso para " + emailDestino);
    }
}