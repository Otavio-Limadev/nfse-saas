package br.com.otavio.nfse_saas.controller;

import br.com.otavio.nfse_saas.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO body) {
        // Busca o usuário pelo e-mail no banco de dados
        Optional<UserEntity> userOptional = userRepository.findByEmail(body.getEmail());

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(401).build(); // Erro 401: não autorizado E-mail não existe
        }

        UserEntity user = userOptional.get();

        if (passwordEncoder.matches(body.getPassword(), user.getPassword())) {

            // Se a senha bater gera o cracha Token
            String token = tokenService.gerarToken(user);

            LoginResponseDTO response = new LoginResponseDTO();
            response.setToken(token);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(401).build(); // Erro 401: não autorizado senha incorreta
    }
}