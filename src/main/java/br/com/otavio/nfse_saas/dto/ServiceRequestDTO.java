package br.com.otavio.nfse_saas.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ServiceRequestDTO {
    private String description;
    private BigDecimal value;
    private Double taxRate;
}