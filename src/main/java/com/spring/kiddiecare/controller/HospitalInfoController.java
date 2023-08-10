package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.user.User;
import com.spring.kiddiecare.domain.user.UserRepository;
import com.spring.kiddiecare.util.OpenApiDataUtil;
import com.spring.kiddiecare.util.hospbasis.HospBasisResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Duration;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class HospitalInfoController {
    private final UserRepository userRepository;
    private final OpenApiDataUtil openApiDataUtil;
//    private final OpenApiDataUtil openApiDataUtil;
    private String baseUrl = "https://apis.data.go.kr/B551182/";
    private String hospInfoService="hospInfoServicev2/getHospBasisList?ServiceKey=";
    private String admDtlInfoService="MadmDtlInfoService2/getTrnsprtInfo2?serviceKey=";
    private String a= "getDtlInfo2";

    @Value("${myapp.decode}")
    private String decodeServiceKey;
    @Value("${myapp.encode}")
    private String encodeServiceKey;

    /**
     외부 API에서 병원 정보를 가져와서 모델에 추가하고, externalData 템플릿을 렌더링한다.
     @param model 렌더링할 데이터를 담을 모델 객체
     @return externalData 템플릿을 렌더링한 결과
     */
    @GetMapping("search/list")
    public String fetchExternalData(Model model){
        String url = baseUrl + hospInfoService + encodeServiceKey;
        Duration cacheTtl = Duration.ofMinutes(1);

        HospBasisResponse apiResponse = openApiDataUtil.hosInfoList(url, cacheTtl);
        model.addAttribute("list", apiResponse);
        return "externalData";
    }

    /**
     외부 API에서 병원 상세 정보를 가져와서 모델에 추가하고, hospitalDetail 템플릿을 렌더링한다.
     @param model 렌더링할 데이터를 담을 모델 객체
     @return externalData 템플릿을 렌더링한 결과
     */
    @GetMapping("/search/detail")
    public String detailSearch(@RequestParam String ykiho, Model model){
        String url = baseUrl + admDtlInfoService + encodeServiceKey + "&ykiho=" + ykiho ;
        System.out.println(url);
        Duration cacheTtl = Duration.ofMinutes(1);

        HospBasisResponse apiResponse = openApiDataUtil.fetchDataClass(url, cacheTtl);
        model.addAttribute("apiResponse", apiResponse);
        return "hospitalDetail";
    }


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
            String url = baseUrl + encodeServiceKey + pageNo + dgsbjtCd + xpos1 + ypos1 + radius1;

            Duration cacheTtl = Duration.ofSeconds(5);

            HospBasisResponse response = openApiDataUtil.fetchDataClass(url, cacheTtl);
            model.addAttribute("response", response);


        }
        return "externalData";
    }

}
