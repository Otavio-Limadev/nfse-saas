package br.com.otavio.nfse_saas.service;

import br.com.otavio.nfse_saas.Company;
import br.com.otavio.nfse_saas.CompanyRepository;
import br.com.otavio.nfse_saas.Customer;
import br.com.otavio.nfse_saas.CustomerRepository;
import br.com.otavio.nfse_saas.dto.CustomerRequestDTO;
import br.com.otavio.nfse_saas.dto.CustomerResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CompanyRepository companyRepository;

    public CustomerService(CustomerRepository customerRepository, CompanyRepository companyRepository) {
        this.customerRepository = customerRepository;
        this.companyRepository = companyRepository;
    }

    public CustomerResponseDTO createCustomer(Long companyId, CustomerRequestDTO dto) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada!"));

        Customer customer = new Customer();
        customer.setCompany(company);
        customer.setName(dto.getName());
        customer.setTaxId(dto.getTaxId());
        customer.setEmail(dto.getEmail());
        customer.setAddress(dto.getAddress());

        Customer savedCustomer = customerRepository.save(customer);

        CustomerResponseDTO response = new CustomerResponseDTO();
        response.setId(savedCustomer.getId());
        response.setName(savedCustomer.getName());
        response.setTaxId(savedCustomer.getTaxId());
        response.setEmail(savedCustomer.getEmail());

        return response;
    }

    // listar
    public List<CustomerResponseDTO> findAllByCompany(Long companyId) {
        return customerRepository.findByCompanyId(companyId).stream()
                .map(customer -> {
                    CustomerResponseDTO dto = new CustomerResponseDTO();
                    dto.setId(customer.getId());
                    dto.setName(customer.getName());
                    dto.setTaxId(customer.getTaxId());
                    dto.setEmail(customer.getEmail());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}