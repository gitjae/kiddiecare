package com.spring.kiddiecare.util;

import java.security.SecureRandom;

public class RandomUtil {
    private static final int STRING_LENGTH = 8;

    public static String createRanNum() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(STRING_LENGTH);

        for (int i = 0; i < STRING_LENGTH; i++) {
            int randomDigit = random.nextInt(10); // Generate random digit (0-9)
            sb.append(randomDigit);
        }

        return sb.toString();
    }
}
