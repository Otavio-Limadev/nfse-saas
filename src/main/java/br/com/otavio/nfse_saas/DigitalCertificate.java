package br.com.otavio.nfse_saas;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class DigitalCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Garante que uma empresa só tenha UM certificado ativo por vez
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false, unique = true)
    private Company company;

    @Column(nullable = false)
    private String fileName;

    @Column(name = "file_data", columnDefinition = "BYTEA")
    private byte[] fileData;

    @Column(nullable = false)
    private String encryptedPassword; // criptografar a senha na hora de salvar

    private LocalDateTime expirationDate; // Para avisar o cliente quando estiver vencendo

    private boolean active = true;

    private LocalDateTime createdAt = LocalDateTime.now();

    public String getPassword() {
        return this.encryptedPassword;
    }
}