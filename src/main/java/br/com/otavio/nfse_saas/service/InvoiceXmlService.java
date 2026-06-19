package br.com.otavio.nfse_saas.service;

import br.com.otavio.nfse_saas.InvoiceEntity;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class InvoiceXmlService {

    private final TemplateEngine templateEngine;

    public InvoiceXmlService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String generateXml(InvoiceEntity invoice) {
        Context context = new Context();
        context.setVariable("invoice", invoice);
        return templateEngine.process("nota_fiscal", context);
    }
}