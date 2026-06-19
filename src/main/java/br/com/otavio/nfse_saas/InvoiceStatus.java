package br.com.otavio.nfse_saas;

public enum InvoiceStatus {
    PENDENTE,          // temporario
    DRAFT,             // Rascunho
    READY_TO_ISSUE,    // Pronta para emitir
    PROCESSING,        // Em processamento
    ISSUED,            // Emitida
    REJECTED,          // Rejeitada
    CANCELED,          // Cancelada
    ERROR              // Erro de comunicação
}