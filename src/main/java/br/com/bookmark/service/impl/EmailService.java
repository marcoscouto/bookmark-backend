package br.com.bookmark.service.impl;

import br.com.bookmark.exception.EmailException;
import br.com.bookmark.service.EmailServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Service
public class EmailService implements EmailServiceInterface {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    private SimpleMailMessage sm;

    @Override
    public void sendAccountConfirmationEmail(String email, String name) {
        try {
            javaMailSender.send(prepareAccountConfirmationEmail(email, name));
        } catch (EmailException e) {
            throw new EmailException("O envio de e-mail falhou");
        }
    }

    @Override
    public void sendChangePasswordEmail(String email, String password) {
        try {
            javaMailSender.send(prepareForgotPasswordEmail(email, password));
        } catch (EmailException e) {
            throw new EmailException("O envio de e-mail falhou");
        }
    }

    private MimeMessage prepareAccountConfirmationEmail(String email, String name) {
        String subject = "[BOOKMARK] Olá " + name + ", confirme já sua conta no Bookmark!";
        String text = "<html>" +
                "<body><b style=\"color: red\">Confirme seu email</b> " +
                "<br>clicando no seguinte link: https://www.google.com.br</body>" +
                "</html>";

        return prepareEmail(email, subject, text);
    }

    private MimeMessage prepareForgotPasswordEmail(String email, String password) {
        String subject = "[BOOKMARK] Esqueceu sua senha? Não se preocupe!";
        String text = "<html>" +
                "<body><b>Aqui está sua nova senha</b> " +
                "<br>A sua nova senha é: " + password +
                "<br>Mas atenção, troque assim que possível!" +
                "</body></html>";

        return prepareEmail(email, subject, text);
    }

    public MimeMessage prepareEmail(String email, String subject, String text) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo(email);
            helper.setFrom(sender);
            helper.setSubject(subject);
            helper.setText(text, true);
            return message;
        } catch (MessagingException e) {
            throw new EmailException("O envio de e-mail falhou");
        }
    }

}
