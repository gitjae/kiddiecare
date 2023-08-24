package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.hospitalAdmin.AdminRequestDto;
import com.spring.kiddiecare.domain.hospitalInfo.HospData;
import com.spring.kiddiecare.util.OpenApiDataUtil;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.context.request.WebRequest;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@RestController
@RequiredArgsConstructor
@RequestMapping("email")
public class EmailVerificationController {

    private final JavaMailSender mailSender;
    private final OpenApiDataUtil openApiDataUtil;

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

    @PostMapping("create")
    public Map sendVerificationEmail(@ModelAttribute AdminRequestDto adminDto) {
        JSONObject resultJson = new JSONObject();
        String verificationCode = getAuthToken();
        String verificationDuration = getVerificationDuration();
        String target = adminDto.getAdminEmail();

        System.out.println(adminDto);

        if(target != null){
            try{
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setFrom(new InternetAddress("oeglks601@gmail.com","kiddiecare"));
                helper.setTo(target);
                helper.setSubject("[kiddiecare] 이메일 확인 코드");
                helper.setText("[kiddiecare] 이메일 확인 코드 : " + verificationCode);
                mailSender.send(message);
            }catch (Exception e){
                resultJson.put("response", "fail cause email sender");
            }
            openApiDataUtil.saveEmailAuthToken(target,verificationCode);
            resultJson.put("response", "success");
            resultJson.put("verification_code", verificationCode);
            resultJson.put("verification_duration", verificationDuration);
        }else{
            resultJson.put("response", "fail cause target is null");
        }

        return resultJson.toMap();
    }

    @PostMapping("validate")
    public Map validateVerificationCode(@ModelAttribute AdminRequestDto adminDto) {
        JSONObject resultJson = new JSONObject();
        String result = openApiDataUtil.getEmailAuthToken(adminDto.getAdminEmail());
        if (result == null) {
            return resultJson.put("response","fail cause timeout").toMap();
        }

        if(!result.equals(adminDto.getCode())){
            return resultJson.put("response","fail cause not matched").toMap();
        }

        return resultJson.put("response","success").toMap();
    }

}
