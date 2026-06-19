package br.com.otavio.nfse_saas;

public interface FileStorageService {
    NotaArquivosEntity saveFile(byte[] content, String fileName, String contentType, InvoiceEntity invoice, String tipo);

    NotaArquivosEntity findById(Long id);
}