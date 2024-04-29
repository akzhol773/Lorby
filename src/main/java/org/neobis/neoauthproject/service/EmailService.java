package org.neobis.neoauthproject.service;


import org.neobis.neoauthproject.entity.User;

public interface EmailService {
    void prepareMail(String link, User user);
    void sendEmail(String to, String body);

    void sendConfirmationMail(String link, User user);
}
