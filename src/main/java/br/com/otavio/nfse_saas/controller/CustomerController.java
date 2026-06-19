package br.com.otavio.nfse_saas.controller;

import br.com.otavio.nfse_saas.dto.CustomerRequestDTO;
import br.com.otavio.nfse_saas.dto.CustomerResponseDTO;
import br.com.otavio.nfse_saas.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies/{companyId}/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // criar um cliente
    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(
            @PathVariable Long companyId,
            @RequestBody CustomerRequestDTO requestDTO) {

        CustomerResponseDTO response = customerService.createCustomer(companyId, requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // listar clientes da empresa
    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> findAll(@PathVariable Long companyId) {
        return ResponseEntity.ok(customerService.findAllByCompany(companyId));
    }
}