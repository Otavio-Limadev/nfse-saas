package br.com.otavio.nfse_saas;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(nullable = false)
    private String name; // Nome ou Razão Social

    @Column(nullable = false)
    private String taxId; // CPF ou CNPJ

    private String email;

    private String address;
}