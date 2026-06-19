package br.com.otavio.nfse_saas;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {

    // (.env)
    private final String secret = "minha-chave-secreta-super-protegida";

    public String gerarToken(UserEntity user) {
        return JWT.create()
                .withIssuer("nfse-saas")
                .withSubject(user.getEmail())
                .withExpiresAt(Instant.now().plus(2, ChronoUnit.HOURS)) // O token dura 2 horas
                .sign(Algorithm.HMAC256(secret));
    }

    public String validarToken(String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("nfse-saas")
                .build()
                .verify(token)
                .getSubject();
    }
}