package br.com.bookmark.service.impl;

import br.com.bookmark.exception.EmailException;
import br.com.bookmark.service.EmailServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService implements EmailServiceInterface {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void sendAccountConfirmationEmail(String email, String name) {
        try {
            javaMailSender.send(prepareAccountConfirmationEmail(email, name));
        } catch (EmailException e) {
            throw new EmailException("O envio de e-mail falhou");
        }
    }

    @Override
    public void sendChangePasswordEmail(String email, String name, String password) {
        try {
            javaMailSender.send(prepareForgotPasswordEmail(email, name, password));
        } catch (EmailException e) {
            throw new EmailException("O envio de e-mail falhou");
        }
    }

    private MimeMessage prepareAccountConfirmationEmail(String email, String name) {
        String subject = "[BOOKMARK] Olá " + name + ", confirme já sua conta no Bookmark!";
        String text = templateAccountConfirmationEmail(name);
        return prepareEmail(email, subject, text);
    }

    private MimeMessage prepareForgotPasswordEmail(String email, String name, String password) {
        String subject = "[BOOKMARK] Esqueceu sua senha? Não se preocupe!";
        String text = templateForgotPasswordEmail(name, password);
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

    private String templateAccountConfirmationEmail(String name) {
        Context context = new Context();
        context.setVariable("name", name);
        return templateEngine.process("AccountConfirmationMail.html", context);
    }

    private String templateForgotPasswordEmail(String name, String password) {
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("password", password);
        return templateEngine.process("ForgotPasswordMail.html", context);
    }

}
