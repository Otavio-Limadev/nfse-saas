package br.com.otavio.nfse_saas.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mock-prefeitura")
public class MockPrefeituraController {

    // Finge ser a URL da Prefeitura que recebe o XML assinado
    @PostMapping(value = "/recepcionar", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> recepcionarLote(@RequestBody String xmlEnvio) {

        System.out.println("🏛️ [PREFEITURA MOCK] Recebeu o XML para processamento!");
        System.out.println("Tamanho do XML recebido: " + xmlEnvio.length() + " caracteres.");

        // Retorna um XML de sucesso simulando a aprovação oficial
        String respostaSucesso = "<Retorno><Status>Sucesso</Status><Protocolo>9988776655</Protocolo></Retorno>";

        return ResponseEntity.ok(respostaSucesso);
    }
}