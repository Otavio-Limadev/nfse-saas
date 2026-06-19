package br.com.otavio.nfse_saas.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CompanyResponseDTO {
    private Long id;
    private String corporateName;
    private String tradeName;
    private String taxId;
    private String municipalRegistration;
    private String taxRegime;
    private boolean active;
    private LocalDateTime createdAt;
}