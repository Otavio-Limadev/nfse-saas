package br.com.otavio.nfse_saas;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Company company;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private ServiceEntity serviceItem;

    private Double totalValue;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;

    private String observation;

    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "customer_name_snapshot")
    private String customerNameSnapshot;

    @Column(name = "customer_tax_id_snapshot")
    private String customerTaxIdSnapshot;

    @Column(name = "service_description_snapshot", columnDefinition = "TEXT")
    private String serviceDescriptionSnapshot;

    @Column(name = "service_value")
    private Double serviceValue;
}