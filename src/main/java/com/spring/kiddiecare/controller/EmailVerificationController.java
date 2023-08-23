package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.hospitalAdmin.AdminRequestDto;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.context.request.WebRequest;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Random;

@RestController
@RequiredArgsConstructor
@RequestMapping("email")
public class EmailVerificationController {

    private final JavaMailSender mailSender;

    private String getAuthToken() {
        String randomString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder authToken = new StringBuilder();
        int repeat = 6;

        for (int i = 0; i < repeat; i++) {
            int code = random.nextInt(randomString.length());
            authToken.append(randomString.charAt(code));
        }

        return authToken.toString();
    }

    private String getVerificationDuration() {
        LocalDateTime verificationEndTime = LocalDateTime.now().plusMinutes(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd/HHmmss");
        return verificationEndTime.format(formatter);
    }
    public String getCurrentTime() {
        LocalDateTime current = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd/HHmmss");
        return current.format(formatter);
    }

    @SessionScope
    @PostMapping("create")
    public Map sendVerificationEmail(@ModelAttribute AdminRequestDto adminDto, HttpServletRequest request) {
        JSONObject resultJson = new JSONObject();
        String verificationCode = getAuthToken();
        String verificationDuration = getVerificationDuration();
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(new InternetAddress("oeglks601@gmail.com","kiddiecare"));
            helper.setTo(adminDto.getAdminEmail());
            helper.setSubject("[kiddiecare] 이메일 확인 코드");
            helper.setText("[kiddiecare] 이메일 확인 코드 : " + verificationCode);
            mailSender.send(message);

            HttpSession session = request.getSession();

            session.setAttribute("verification_code", verificationCode);
            request.setAttribute("verification_duration", verificationDuration);

            resultJson.put("result", "VERIFICATION_SENT");
            resultJson.put("verification_code", verificationCode);
            resultJson.put("verification_duration", verificationDuration);
        }catch (Exception e){
            System.out.println(e);
            resultJson.put("result", "FAIL");
        }

        return resultJson.toMap();
    }

    @PostMapping("validate")
    public Map validateVerificationCode(HttpServletRequest request, String verificationCode) {
        JSONObject resultJson = new JSONObject();
        HttpSession session = request.getSession();
        String savedCode = (String) session.getAttribute("verification_code");
        String savedDuration = (String) session.getAttribute("verification_duration");

        if(savedCode != null || savedDuration != null){
            resultJson.put("result", "VERIFICATION_FAILED");
        }

        System.out.println(savedCode+"savedCode");
        System.out.println(savedDuration+"savedDuration");

        String[] currentTime = getCurrentTime().split("/");
        String[] Duration = savedDuration.split("/");

        if(currentTime[0].equals(Duration[0])){
            try{
                int now = Integer.parseInt(currentTime[1]);
                int duTime = Integer.parseInt(Duration[1]);
                String message = now <= duTime ? "VERIFICATION_SUCCEEDED" : "EXPIRED";
                if(verificationCode.equals(savedCode)){
                    resultJson.put("result",message);
                    session.removeAttribute("verification_code");
                    session.removeAttribute("verification_duration");
                    session.invalidate(); // 세션 무효화
                }else{
                    resultJson.put("result","fail");
                }
            }catch (Exception e){
                resultJson.put("result", "VERIFICATION_FAILED");
            }
        }else {
            resultJson.put("result", "VERIFICATION_FAILED");
        }

        return resultJson.toMap();
    }


}
