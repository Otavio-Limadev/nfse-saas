package br.com.otavio.nfse_saas.dto;

import lombok.Data;

@Data
public class CustomerResponseDTO {
    private Long id;
    private String name;
    private String taxId;
    private String email;
}