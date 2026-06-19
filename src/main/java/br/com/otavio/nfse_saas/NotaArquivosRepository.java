package br.com.otavio.nfse_saas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotaArquivosRepository extends JpaRepository<NotaArquivosEntity, Long> {
    // permite buscar todos os arquivos de uma nota específica
    List<NotaArquivosEntity> findByInvoiceId(Long invoiceId);
}