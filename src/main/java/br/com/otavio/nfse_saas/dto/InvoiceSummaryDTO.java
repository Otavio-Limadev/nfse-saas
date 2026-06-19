package br.com.otavio.nfse_saas.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class InvoiceSummaryDTO {
    private Long invoiceId;
    private String customerName;
    private String customerTaxId;
    private String serviceDescription;
    private BigDecimal totalValue;
    private String status;
    private LocalDateTime createdAt;
}