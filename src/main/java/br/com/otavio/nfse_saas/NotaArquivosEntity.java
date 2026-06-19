package br.com.otavio.nfse_saas;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "nota_arquivos")
@Data
public class NotaArquivosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", nullable = false)
    private InvoiceEntity invoice;

    @Column(nullable = false)
    private String tipo; // PDF ou XML

    @Column(nullable = false)
    private String storageUrl;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String contentType;

    // Salva os bytes do arquivo no banco
    @Column(columnDefinition = "BYTEA")
    private byte[] content;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}