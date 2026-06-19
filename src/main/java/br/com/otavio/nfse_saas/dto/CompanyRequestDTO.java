package br.com.otavio.nfse_saas.dto;

import lombok.Data;

@Data
public class CompanyRequestDTO {
    private String corporateName;
    private String tradeName;
    private String taxId;
    private String municipalRegistration;
    private String taxRegime;
}