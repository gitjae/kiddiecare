package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.user.User;
import com.spring.kiddiecare.domain.user.UserRepository;
import com.spring.kiddiecare.domain.user.UserRequestDto;
import com.spring.kiddiecare.util.KakaoLoginManager;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;

@SessionAttributes({"log"})
@RequiredArgsConstructor
@RestController
public class KakaoLoginController {

    private final UserRepository userRepository;
    KakaoLoginManager loginManager = new KakaoLoginManager();
    String codeRequrl = "";

    @GetMapping("/login/kakao/callback")
    public ModelAndView kakaoCallback(@RequestParam String code) {
        ModelAndView modelAndView = new ModelAndView();
        // 엑세스 토큰 요청
        String accessToken = loginManager.getKakaoAccessToken(code);

        // 카카오 사용자 정보 요청 및 가져오기
        String email = loginManager.getKakaoUserInfo(accessToken);
        System.out.println("email:"+email);

        // 사용자 정보를 확인하고, 가입되지 않은 사용자일 경우 추가 정보 입력을 위해 회원가입 페이지로 보냅니다.
        Optional<User> foundUser = userRepository.findUserById(email);
        if(foundUser.isEmpty()){
            modelAndView.setViewName("kakaoJoin");
            modelAndView.addObject("email", email);
            return modelAndView;
        } else {
            // 이미 가입된 사용자라면 계정 정보를 로드하고 로그인 처리를 진행합니다.
            User user = foundUser.get();
            modelAndView.setViewName("index");
            modelAndView.addObject("log", user.getId());
            return modelAndView;
        }
    }

    @GetMapping("login/kakao")
    public String kakaoLogin(@RequestParam String code){
        String url = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="
                + loginManager.getKAKAO_REST_API_KEY() + "&redirect_uri=" + loginManager.redirect_uri;

        if(code != null){
            return url;
        }

        String accessToken = loginManager.getKakaoAccessToken(code);
        String email = loginManager.getKakaoUserInfo(accessToken);

        UserRequestDto userDto = new UserRequestDto();
        userDto.setEmail(email);

        return null;
    }
}
