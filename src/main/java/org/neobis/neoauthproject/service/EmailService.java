package org.neobis.neoauthproject.service;


import org.neobis.neoauthproject.entity.User;

public interface EmailService {
    void sendConfirmMail(String link, User user);
    void prepareConfirmMail(String to, String body);
    void prepareForgotPasswordMail(String to, String body);
    void sendForgotPasswordMail(String link, User user);
}
