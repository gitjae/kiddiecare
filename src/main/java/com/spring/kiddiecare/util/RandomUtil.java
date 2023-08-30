package com.spring.kiddiecare.util;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// 난수5자리 생성 yymmdd12345 -> ex) 23081454751
public class RandomUtil {
//    private static final int STRING_LENGTH = 8;
    private static final int STRING_LENGTH = 5;

    public static String createRanNum() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(STRING_LENGTH);

        for (int i = 0; i < STRING_LENGTH; i++) {
            int randomDigit = random.nextInt(10); // random 생성 (0-9)
            sb.append(randomDigit);
        }

        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd"); //ex) 23080988
        String formdate = now.format(formatter);

        String result = formdate + sb;

//        return sb.toString();
        return result;
    }

    /**
     * 전화번호 인증용 난수 6자리 생성
     * @return 난수 6자리
     */
    public String makeRandomCode(){
        String code = "";

        for(int i=0;i<6;i++){
            int r = (int) (Math.random() * 10);
            code += String.valueOf(r);
        }

        return code;
    }
}
