package br.com.otavio.nfse_saas;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String email;
    private String password;
}