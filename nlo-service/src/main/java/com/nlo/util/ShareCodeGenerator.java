package com.nlo.util;

import java.security.SecureRandom;

public class ShareCodeGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateShareCode() {
        StringBuilder shareCode = new StringBuilder(15);
        for (int i = 0; i < 15; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            shareCode.append(CHARACTERS.charAt(index));
        }
        return shareCode.toString();
    }
}
