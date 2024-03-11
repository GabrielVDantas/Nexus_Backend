package br.com.nexus.Nexus.util;

import java.security.SecureRandom;

public class RandomCodeGenerator {

    private static final String CARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String generateRandomCode(int length) {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            var index = secureRandom.nextInt(CARACTERS.length());
            stringBuilder.append(CARACTERS.charAt(index));
        }
        return stringBuilder.toString();
    }
}
