package br.com.otavio.nfse_saas.service;

import br.com.otavio.nfse_saas.Company;
import br.com.otavio.nfse_saas.CompanyRepository;
import br.com.otavio.nfse_saas.DigitalCertificate;
import br.com.otavio.nfse_saas.DigitalCertificateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class DigitalCertificateService {

    private final DigitalCertificateRepository certificateRepository;
    private final CompanyRepository companyRepository;

    public DigitalCertificateService(DigitalCertificateRepository certificateRepository, CompanyRepository companyRepository) {
        this.certificateRepository = certificateRepository;
        this.companyRepository = companyRepository;
    }

    @Transactional
    public String uploadCertificate(Long companyId, MultipartFile file, String password) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("O arquivo do certificado está vazio ou não foi enviado.");
        }

        System.out.println("Processando upload para Empresa ID: " + companyId);
        System.out.println("Tamanho do arquivo: " + file.getSize() + " bytes");

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada com ID: " + companyId));

        DigitalCertificate cert = certificateRepository.findByCompanyId(companyId)
                .orElse(new DigitalCertificate());

        cert.setCompany(company);
        cert.setFileName(file.getOriginalFilename());

        cert.setFileData(file.getBytes());

        cert.setEncryptedPassword(password);

        cert.setExpirationDate(LocalDateTime.now().plusYears(1));
        cert.setActive(true);

        certificateRepository.save(cert);

        return "Certificado salvo com sucesso para a empresa: " + company.getTradeName();
    }
}