package br.com.otavio.nfse_saas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {
    List<InvoiceEntity> findByCompanyId(Long companyId);
    Optional<InvoiceEntity> findByIdAndCompanyId(Long id, Long companyId);
}