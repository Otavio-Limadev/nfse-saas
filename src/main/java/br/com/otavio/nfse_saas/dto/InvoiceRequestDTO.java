package br.com.otavio.nfse_saas.dto;

import lombok.Data;

@Data
public class InvoiceRequestDTO {
    private Long customerId;
    private Long serviceId;
    private String observation;
}