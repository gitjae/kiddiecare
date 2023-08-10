package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.user.User;
import com.spring.kiddiecare.domain.user.UserRepository;
import com.spring.kiddiecare.util.OpenApiDataUtil;
import com.spring.kiddiecare.util.hospbasis.HospBasisBody;
import com.spring.kiddiecare.util.hospbasis.HospBasisItem;
import com.spring.kiddiecare.util.hospbasis.HospBasisResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class HospitalInfoController {
    private final UserRepository userRepository;
    private final OpenApiDataUtil openApiDataUtil;

    private String baseUrl = "https://apis.data.go.kr/B551182/";
    private String hospInfoService="hospInfoServicev2/";
    private String admDtlInfoService="MadmDtlInfoService2/";
    private String HospList = "getHospBasisList";
    private String getDtlInfo = "getDtlInfo2";
    private String getDgsbjtInfo = "getDgsbjtInfo2?";
    private String getTrnsprtInfo  = "getTrnsprtInfo2?";

    private String pageNo = "&pageNo=";
    private String yadmNm = "&yadmNm=";
    private String ykiho = "&ykiho=";
    private String xPos = "&xPos=";
    private String yPos = "&yPos=";
    private String radius = "&radius=200";

    @Value("${external.api.decode}")
    private String decodeServiceKey;
    @Value("${external.api.encode}")
    private String encodeServiceKey;

    /**
     외부 API에서 병원 정보를 가져와서 모델에 추가하고, externalData 템플릿을 렌더링한다.
     @param model 렌더링할 데이터를 담을 모델 객체
     @return externalData 템플릿을 렌더링한 결과
     */
    @GetMapping("search/list")
    public String getHospList(Model model, @RequestParam(defaultValue="") String keyword,
                              @RequestParam(defaultValue="1") String requestPageNo){

        String encodedParamValue = null;
        try {
            encodedParamValue = URLEncoder.encode(keyword, "UTF-8");
        }catch (UnsupportedEncodingException e){
            return "hospitalSearchList";
        }

        // 리스트 불러오기
        String uri = pageNo + requestPageNo + yadmNm + encodedParamValue;
        String url = baseUrl + hospInfoService + HospList + encodeServiceKey + uri;
        Duration cacheTtl = Duration.ofMinutes(1);
        HospBasisBody response = openApiDataUtil.hosInfoList(url, uri, cacheTtl);

        int maxPageCount = response.getTotalCount()/response.getNumOfRows();
        int currentPage = response.getPageNo();

        for(HospBasisItem item : response.getItems()){
            String hospInfoUri = ykiho + item.getYkiho() + "&_type=json";
            String hospInfoUrl = baseUrl + admDtlInfoService + getDtlInfo + encodeServiceKey +hospInfoUri;
            System.out.println("Url: "+hospInfoUrl);
            HospBasisBody apiResponse = openApiDataUtil.hosInfoDetail(hospInfoUrl, hospInfoUri,cacheTtl);
        }

        model.addAttribute("response", response);
        return "hospitalSearchList";
    }


    /**
     외부 API에서 병원 상세 정보를 가져와서 모델에 추가하고, hospitalDetail 템플릿을 렌더링한다.
     @param model 렌더링할 데이터를 담을 모델 객체
     @return externalData 템플릿을 렌더링한 결과
     */
    @GetMapping("/search/detail")
    public String getHospDetail(@RequestParam String requestYkiho, Model model){
        String url = baseUrl + admDtlInfoService + encodeServiceKey + ykiho + requestYkiho ;
        Duration cacheTtl = Duration.ofMinutes(1);

//        HospBasisResponse apiResponse = openApiDataUtil.fetchDataClass(url, cacheTtl);
//        model.addAttribute("apiResponse", apiResponse);
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

//            HospBasisResponse response = openApiDataUtil.fetchDataClass(url, cacheTtl);
//            model.addAttribute("response", response);
        }
        return "externalData";
    }

}
