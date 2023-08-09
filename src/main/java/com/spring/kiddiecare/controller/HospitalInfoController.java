package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.hospitalInfo.HospitalInfo;
import com.spring.kiddiecare.domain.user.User;
import com.spring.kiddiecare.domain.user.UserRepository;
import com.spring.kiddiecare.service.ExternalApiService;
import com.spring.kiddiecare.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;

import java.time.Duration;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class HospitalInfoController {
    @Autowired
    private ExternalApiService externalApiService;
    @Autowired
    private final UserRepository userRepository;


    @Value("${myapp.decode}")
    private String decode;

    @Value("${myapp.encode}")
    private String encode;

    private String x = "128.5493894";
    private String y = "35.9348614";
    private String r = "100";
    private String xpos = "&xPos=" + x;
    private String ypos = "&yPos=" + y;

    private String radius = "&radius=" + r;

    @GetMapping("/fetch")
    public String fetchExternalData(Model model){
        String externalApiUrl =
                "https://apis.data.go.kr/B551182/hospInfoServicev2/getHospBasisList?ServiceKey=" + encode;

        Duration cacheTtl = Duration.ofMinutes(1);

        ApiResponse apiResponse = externalApiService.fetchData2(externalApiUrl, cacheTtl);
        model.addAttribute("apiResponse", apiResponse);

        return "externalData";
    }

    /**
    TODO 검색창에서 키워드를 검색하여 결과를 출력하는 로직
     request
     ServiceKey : encode
     pageNo: params에서 받아오기
     dgsbjtCd: 11 (소아청소년과 진료코드)
     yadmNm: 병원이름
     */


    // TODO 검색결과를 클릭시 병원 상세 정보가 나오는 로직


    // TODO 내 위치 기반으로 반경 200M 안에 있는 병원 목록이 나오는 로직

    // TODO 사용자 집 위치 기반으로 반경 200M 안에 있는 병원 목록이 나오는 로직
    @GetMapping("/home")
    public String home(Model model){
        //String log = (String) request.getAttribute("log", WebRequest.SCOPE_SESSION);
        String log = "redberry";

        Optional<User> foundUser = userRepository.findUserById(log);
        if(foundUser.isPresent()){
            User user = foundUser.get();
            String pageNo = "&pageNo=1";
            String dgsbjtCd = "&dgsbjtCd=11";
            String xpos1 = "&xPos="+user.getXpos();
            String ypos1 = "&yPos="+user.getYpos();
            String radius1 = "&radius=200";

            String baseUrl = "https://apis.data.go.kr/B551182/hospInfoServicev2/getHospBasisList?ServiceKey=";
            String url = baseUrl + encode + pageNo + dgsbjtCd + xpos1 + ypos1 + radius1;

            Duration cacheTtl = Duration.ofSeconds(5);

            ApiResponse apiResponse = externalApiService.fetchData(url, cacheTtl);
            model.addAttribute("apiResponse", apiResponse);


        }
        return "externalData";
    }


    // TODO 회원가입시 도로명 주소로 x,y좌표 저장하는 로직

}
