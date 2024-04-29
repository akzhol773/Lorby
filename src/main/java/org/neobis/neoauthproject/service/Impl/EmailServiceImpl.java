package org.neobis.neoauthproject.service.Impl;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.neobis.neoauthproject.entity.User;
import org.neobis.neoauthproject.service.EmailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;


@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine engine;


    @Override
    public void sendEmail(String to, String body) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(body, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom("lorby@edu.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException("Failed to send email");
        }

    }

    @Override
    public void prepareConfirmationMail(String link, User user) {
        Context context = new Context();
        context.setVariable("confirmEmailUrl", link);
        String emailBody = engine.process("confirmation_email", context);
        sendConfirm(user.getEmail(), emailBody);

    }

    @Override
    public void prepareMail(String link, User user){
        Context context = new Context();
        context.setVariable("confirmEmailUrl", link);
        String emailBody = engine.process("confirmation_email", context);
       sendEmail(user.getEmail(), emailBody);
    }

    @Override
    public void sendConfirm(String to, String body) {
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(body, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom("lorby@edu.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException("Failed to send email");
        }

    }





}
