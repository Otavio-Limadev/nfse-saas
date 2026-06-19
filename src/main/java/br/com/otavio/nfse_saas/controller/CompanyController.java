package br.com.otavio.nfse_saas.controller;

import br.com.otavio.nfse_saas.dto.CompanyRequestDTO;
import br.com.otavio.nfse_saas.dto.CompanyResponseDTO;
import br.com.otavio.nfse_saas.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor //
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyResponseDTO> createCompany(@RequestBody CompanyRequestDTO requestDTO) {
        CompanyResponseDTO responseDTO = companyService.createCompany(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    // listar empresas
    @GetMapping
    public ResponseEntity<List<CompanyResponseDTO>> findAll() {
        List<CompanyResponseDTO> companies = companyService.findAll();
        return ResponseEntity.ok(companies);
    }
}