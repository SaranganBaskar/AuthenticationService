package dev.sarangan.authenticationservice.services;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class Utilities {
    public static String generateSessionToken(int defaultTokenLength){
        final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                "abcdefghijklmnopqrstuvwxyz" +
                "0123456789";
        SecureRandom random = new SecureRandom();

        StringBuilder sb = new StringBuilder(defaultTokenLength);
        for (int i = 0; i < defaultTokenLength; i++)
            sb.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        return sb.toString();
    }
}
