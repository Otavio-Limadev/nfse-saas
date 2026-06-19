package br.com.otavio.nfse_saas;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // MULTI-TENANT:
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    // Snapshot
    @Column(nullable = false)
    private String customerNameSnapshot;

    @Column(nullable = false)
    private String customerTaxIdSnapshot;

    @Column(nullable = false)
    private String serviceDescriptionSnapshot;

    // Valores e impostos
    @Column(nullable = false)
    private BigDecimal serviceValue;

    private BigDecimal taxValue; // Valor calculado do ISS

    // Controle / arquivos da prefeitura
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvoiceStatus status;

    private String officialNumber; // O número gerado na prefeitura
    private String pdfUrl;
    private String xmlUrl;

    private LocalDateTime createdAt = LocalDateTime.now();
}