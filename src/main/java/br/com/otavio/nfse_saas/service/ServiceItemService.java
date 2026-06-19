package br.com.otavio.nfse_saas.service;

import br.com.otavio.nfse_saas.*;
import br.com.otavio.nfse_saas.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceItemService {
    private final ServiceItemRepository repository;
    private final CompanyRepository companyRepository;

    public ServiceItemResponseDTO create(Long companyId, ServiceItemRequestDTO dto) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada!"));

        ServiceItem item = new ServiceItem();
        item.setCompany(company);
        item.setDescription(dto.getDescription());
        item.setValue(dto.getValue());

        ServiceItem saved = repository.save(item);

        ServiceItemResponseDTO res = new ServiceItemResponseDTO();
        res.setId(saved.getId());
        res.setDescription(saved.getDescription());
        res.setValue(saved.getValue());
        return res;
    }

    public List<ServiceItemResponseDTO> findAll(Long companyId) {
        return repository.findByCompanyId(companyId).stream().map(i -> {
            ServiceItemResponseDTO res = new ServiceItemResponseDTO();
            res.setId(i.getId());
            res.setDescription(i.getDescription());
            res.setValue(i.getValue());
            return res;
        }).collect(Collectors.toList());
    }
}