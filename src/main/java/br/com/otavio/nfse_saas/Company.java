package br.com.otavio.nfse_saas;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String corporateName; // Razão Social

    private String tradeName; // Nome Fantasia

    @Column(nullable = false, unique = true)
    private String taxId; // CNPJ

    private String municipalRegistration; // Inscrição Municipal
    private String taxRegime; // Regime Tributário

    // Controle interno
    private LocalDateTime createdAt = LocalDateTime.now();
    private boolean active = true;
}