package br.com.otavio.nfse_saas.service;

import br.com.otavio.nfse_saas.*;
import br.com.otavio.nfse_saas.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceService {
    private final ServiceItemRepository repository;
    private final CompanyRepository companyRepository;

    public ServiceResponseDTO createService(Long companyId, ServiceRequestDTO dto) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada!"));

        ServiceItem item = new ServiceItem();
        item.setCompany(company);
        item.setDescription(dto.getDescription());
        item.setValue(dto.getValue());

        ServiceItem saved = repository.save(item);

        ServiceResponseDTO res = new ServiceResponseDTO();
        res.setId(saved.getId());
        res.setDescription(saved.getDescription());
        res.setValue(saved.getValue());
        return res;
    }

    //
    public List<ServiceResponseDTO> findAllByCompany(Long companyId) {
        return repository.findByCompanyId(companyId).stream().map(i -> {
            ServiceResponseDTO res = new ServiceResponseDTO();
            res.setId(i.getId());
            res.setDescription(i.getDescription());
            res.setValue(i.getValue());
            return res;
        }).collect(Collectors.toList());
    }
}