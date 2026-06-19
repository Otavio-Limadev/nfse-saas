package br.com.otavio.nfse_saas.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ServiceItemRequestDTO {
    private String description;
    private BigDecimal value;
}