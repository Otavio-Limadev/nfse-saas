package br.com.otavio.nfse_saas;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, CompanyRepository companyRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Company company;
        if (companyRepository.count() == 0) {
            company = new Company();
            company.setCorporateName("Empresa teste SaaS");
            company.setTaxId("12345678000199");
            company.setActive(true);
            company = companyRepository.save(company);
            System.out.println("✅ Empresa de teste criada.");
        } else {
            company = companyRepository.findAll().get(0);
        }

        if (userRepository.count() == 0) {
            UserEntity user = new UserEntity();
            user.setName("Otavio Admin");
            user.setEmail("admin@admin.com");
            user.setPassword(passwordEncoder.encode("123456"));
            user.setCompany(company);

            userRepository.save(user);
            System.out.println("✅ Usuário de teste criado: admin@admin.com / Senha: 123456");
        }
    }
}