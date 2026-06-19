package br.com.otavio.nfse_saas.controller;

import br.com.otavio.nfse_saas.dto.ServiceRequestDTO;
import br.com.otavio.nfse_saas.dto.ServiceResponseDTO;
import br.com.otavio.nfse_saas.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies/{companyId}/services")
@RequiredArgsConstructor // Facilita a injeção do service
public class ServiceController {

    private final ServiceService serviceService;

    @PostMapping
    public ResponseEntity<ServiceResponseDTO> createService(
            @PathVariable Long companyId,
            @RequestBody ServiceRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(serviceService.createService(companyId, dto));
    }

    @GetMapping
    public ResponseEntity<List<ServiceResponseDTO>> findAll(@PathVariable Long companyId) {
        return ResponseEntity.ok(serviceService.findAllByCompany(companyId));
    }
}