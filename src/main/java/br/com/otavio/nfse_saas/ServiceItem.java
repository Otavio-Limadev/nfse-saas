package br.com.otavio.nfse_saas;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "service_items")
public class ServiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Column(precision = 19, scale = 2)
    private BigDecimal value;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
}