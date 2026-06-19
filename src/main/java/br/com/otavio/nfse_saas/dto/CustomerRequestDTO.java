package br.com.otavio.nfse_saas.dto;

import lombok.Data;

@Data
public class CustomerRequestDTO {
    private String name;
    private String taxId; // CPF ou CNPJ
    private String email;
    private String address;
}