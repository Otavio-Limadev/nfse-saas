package br.com.otavio.nfse_saas;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarNotaPorEmail(String para, String nomeCliente, byte[] pdfBytes, String nomeArquivo) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(para);
            helper.setSubject("Sua Nota Fiscal de Serviço");
            String corpoHtml = "<h3>Olá, " + nomeCliente + "!</h3>"
                    + "<p>Sua nota fiscal foi emitida com sucesso. O arquivo PDF está anexado a este e-mail.</p>"
                    + "<br><p>Obrigado por utilizar nossos serviços.</p>";
            helper.setText(corpoHtml, true);
            helper.addAttachment(nomeArquivo, new ByteArrayResource(pdfBytes));

            mailSender.send(message);
            System.out.println("E-mail enviado com sucesso para: " + para);

        } catch (MessagingException e) {
            throw new RuntimeException("Falha ao enviar e-mail com a nota fiscal", e);
        }
    }
}