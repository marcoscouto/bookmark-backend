package br.com.bookmark.service.impl;

import br.com.bookmark.exception.EmailException;
import br.com.bookmark.service.EmailServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EmailService implements EmailServiceInterface {

    @Autowired
    private MailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    private SimpleMailMessage sm;

    @Override
    public void sendAccountConfirmationEmail(String email, String name) {
        try {
            mailSender.send(prepareAccountConfirmationEmail(email, name));
        } catch (EmailException e) {
            throw new EmailException("O envio de e-mail falhou");
        }
    }

    @Override
    public void sendChangePasswordEmail(String email, String password) {
        try {
            mailSender.send(prepareForgotPasswordEmail(email, password));
        } catch (EmailException e) {
            throw new EmailException("O envio de e-mail falhou");
        }
    }

    private SimpleMailMessage prepareAccountConfirmationEmail(String email, String name) {
        sm = new SimpleMailMessage();
        sm.setTo(email);
        sm.setFrom(sender);
        sm.setSubject("[BOOKMARK] Olá " + name + ", confirme já sua conta no Bookmark!");
        sm.setSentDate(new Date());
        sm.setText("Confirme seu email clicando no seguinte link: https://www.google.com.br");
        return sm;
    }

    private SimpleMailMessage prepareForgotPasswordEmail(String email, String password) {
        sm = new SimpleMailMessage();
        sm.setTo(email);
        sm.setFrom(sender);
        sm.setSubject("[BOOKMARK] Esqueceu sua senha? Não se preocupe!");
        sm.setSentDate(new Date());
        sm.setText("A sua nova senha é: " + password + " mas atenção, mude assim que fazer o login em sua conta!");
        return sm;
    }

}
