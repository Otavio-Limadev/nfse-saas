package br.com.otavio.nfse_saas.service;

import br.com.otavio.nfse_saas.Company;
import br.com.otavio.nfse_saas.CompanyRepository;
import br.com.otavio.nfse_saas.dto.CompanyRequestDTO;
import br.com.otavio.nfse_saas.dto.CompanyResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyResponseDTO createCompany(CompanyRequestDTO dto) {
        if (companyRepository.findByTaxId(dto.getTaxId()).isPresent()) {
            throw new RuntimeException("Já existe uma empresa cadastrada com o CNPJ: " + dto.getTaxId());
        }

        Company company = new Company();
        company.setCorporateName(dto.getCorporateName());
        company.setTradeName(dto.getTradeName());
        company.setTaxId(dto.getTaxId());
        company.setMunicipalRegistration(dto.getMunicipalRegistration());
        company.setTaxRegime(dto.getTaxRegime());

        Company savedCompany = companyRepository.save(company);

        return mapToResponse(savedCompany);
    }

    public List<CompanyResponseDTO> findAll() {
        return companyRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private CompanyResponseDTO mapToResponse(Company company) {
        CompanyResponseDTO response = new CompanyResponseDTO();
        response.setId(company.getId());
        response.setCorporateName(company.getCorporateName());
        response.setTradeName(company.getTradeName());
        response.setTaxId(company.getTaxId());
        response.setMunicipalRegistration(company.getMunicipalRegistration());
        response.setTaxRegime(company.getTaxRegime());
        response.setActive(company.isActive());
        response.setCreatedAt(company.getCreatedAt());
        return response;
    }
}