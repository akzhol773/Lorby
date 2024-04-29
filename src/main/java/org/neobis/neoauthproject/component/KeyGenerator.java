package org.neobis.neoauthproject.component;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;


@Component
public class KeyGenerator {
    public static String generateRandomKey() {
        // Define the length of the key
        int keyLength = 64; // You can adjust this length as per your requirements

        // Create a secure random generator
        SecureRandom secureRandom = new SecureRandom();

        // Create a byte array to store the random bytes
        byte[] randomBytes = new byte[keyLength];

        // Fill the byte array with random bytes
        secureRandom.nextBytes(randomBytes);

        // Encode the random bytes using Base64 encoding
        return Base64.getEncoder().encodeToString(randomBytes);
    }


}
