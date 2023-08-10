package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.user.UserRequestDto;
import com.spring.kiddiecare.util.KakaoLoginManager;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class KakaoLoginController {

    KakaoLoginManager loginManager = new KakaoLoginManager();
    String codeRequrl = "";

    @GetMapping("login/kakao")
    public String kakaoLogin(@RequestParam String code){
        String url = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="
                + loginManager.getKAKAO_REST_API_KEY() + "&redirect_uri=" + loginManager.redirect_uri;

        if(code != null){
            return url;
        }

        String accessToken = loginManager.getKakaoAccessToken(code);
        JSONObject jsonObject = loginManager.getKakaoUserInfo(accessToken);

        JSONObject kakaoAccount = jsonObject.getJSONObject("kakao_account");
        String email = kakaoAccount.getString("email");
        String birthday = kakaoAccount.getString("birthday");
        String strGender = kakaoAccount.getString("gender");
        boolean gender = strGender.equals("male") ? true : false;

        UserRequestDto userDto = new UserRequestDto();
        userDto.setEmail(email);
        userDto.setGender(gender);
        userDto.setToken(accessToken);

        // 아오 프론트 하면서 그냥 좀 쉬어야지
        return null;
    }
}
