package br.com.otavio.nfse_saas;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErroPadrao> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
        ErroPadrao erro = new ErroPadrao(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de Requisição / Regra de Negócio",
                ex.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroPadrao> handleException(Exception ex, HttpServletRequest request) {
        ErroPadrao erro = new ErroPadrao(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro Interno no Servidor",
                "Ocorreu um erro inesperado. Contate o suporte.",
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }

    public record ErroPadrao(
            Integer status,
            String erro,
            String mensagem,
            String path,
            LocalDateTime dataHora
    ) {}
}