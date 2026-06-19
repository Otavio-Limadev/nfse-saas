package br.com.otavio.nfse_saas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DigitalCertificateRepository extends JpaRepository<DigitalCertificate, Long> {
    Optional<DigitalCertificate> findByCompanyId(Long companyId);
}