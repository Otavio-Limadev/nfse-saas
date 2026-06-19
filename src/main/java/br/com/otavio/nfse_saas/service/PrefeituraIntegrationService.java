package br.com.otavio.nfse_saas.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PrefeituraIntegrationService {

    public boolean enviarParaPrefeitura(String xmlAssinado) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_XML);

            HttpEntity<String> request = new HttpEntity<>(xmlAssinado, headers);

            String urlPrefeitura = "http://localhost:8080/api/mock-prefeitura/recepcionar";

            System.out.println("🚀 [INTEGRAÇÃO] Disparando requisição para a Prefeitura...");
            ResponseEntity<String> response = restTemplate.postForEntity(urlPrefeitura, request, String.class);

            return response.getStatusCode().is2xxSuccessful();

        } catch (Exception e) {
            System.err.println("❌ Erro ao comunicar com a prefeitura: " + e.getMessage());
            return false;
        }
    }
}