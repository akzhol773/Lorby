package org.neobis.neoauthproject.service;


import org.neobis.neoauthproject.entity.User;

public interface EmailService {
    void prepareMail(String link, User user);
    void sendEmail(String to, String body);

    void prepareConfirmationMail(String link, User user);
    void sendConfirm(String to, String body);
}
