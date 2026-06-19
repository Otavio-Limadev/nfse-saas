package br.com.otavio.nfse_saas;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DatabaseStorageService implements FileStorageService {

    private final NotaArquivosRepository repository;

    public DatabaseStorageService(NotaArquivosRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public NotaArquivosEntity saveFile(byte[] content, String fileName, String contentType, InvoiceEntity invoice, String tipo) {
        NotaArquivosEntity arquivo = new NotaArquivosEntity();
        arquivo.setInvoice(invoice);
        arquivo.setFileName(fileName);
        arquivo.setContentType(contentType);
        arquivo.setTipo(tipo);
        arquivo.setStorageUrl("db://" + fileName);
        arquivo.setContent(content);

        return repository.save(arquivo);
    }

    @Override
    public NotaArquivosEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Arquivo não encontrado com o ID: " + id));
    }
}