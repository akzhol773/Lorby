package org.neobis.neoauthproject.component;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;


@Component
public class KeyGenerator {
    public  String generateRandomKey() {
        int keyLength = 64;
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[keyLength];
        secureRandom.nextBytes(randomBytes);
        return Base64.getEncoder().encodeToString(randomBytes);
    }


}
