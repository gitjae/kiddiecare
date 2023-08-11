package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.user.User;
import com.spring.kiddiecare.domain.user.UserRepository;
import com.spring.kiddiecare.util.CalenderAndGetTrmtUtil;
import com.spring.kiddiecare.util.OpenApiDataUtil;
import com.spring.kiddiecare.util.hospInfo.HospDetailBody;
import com.spring.kiddiecare.util.hospInfo.HospDetailItem;
import com.spring.kiddiecare.util.hospSubInfo.HospSubItem;
import com.spring.kiddiecare.util.hospbasis.HospBasisBody;
import com.spring.kiddiecare.util.hospbasis.HospBasisItem;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class HospitalInfoController {
    private final UserRepository userRepository;
    private final OpenApiDataUtil openApiDataUtil;
    private final CalenderAndGetTrmtUtil calenderAndGetTrmtUtil;

    private String baseUrl = "https://apis.data.go.kr/B551182/";
    private String hospInfoService="hospInfoServicev2/";
    private String admDtlInfoService="MadmDtlInfoService2/";
    private String HospList = "getHospBasisList?";
    private String getDtlInfo = "getDtlInfo2?";
    private String getDgsbjtInfo = "getDgsbjtInfo2?";
    private String getTrnsprtInfo  = "getTrnsprtInfo2?";
    private String pageNo = "&pageNo=";
    private String yadmNm = "&yadmNm=";
    private String ykihoUri = "&ykiho=";
    private String xPos = "&xPos=";
    private String yPos = "&yPos=";
    private String radius = "&radius=200";
//    private Duration cacheTtl = Duration.ofMinutes(3);

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
    public Map getHospList(@RequestParam(defaultValue="") String keyword,
                           @RequestParam(defaultValue="1") String requestPageNo){
        JSONObject result = new JSONObject();
        // keyword 인코딩
        String encodedParamValue = null;
        try {
            encodedParamValue = URLEncoder.encode(keyword, "UTF-8");
        }catch (UnsupportedEncodingException e){
            result.put("result","Encoding Error");
            return result.toMap();
        }

        // hospList 불러오기
        String uri = pageNo + requestPageNo + yadmNm + encodedParamValue;
        String url = baseUrl + hospInfoService + HospList + encodeServiceKey + uri;
        System.out.println("dd "+url);
        Duration cacheTtl = Duration.ofMinutes(3);
        HospBasisBody hospListData = openApiDataUtil.getHospList(url, uri, cacheTtl);

        if(hospListData != null){
            result.put("result","success");
            for(HospBasisItem item : hospListData.getItems()){
                JSONObject dataSet = new JSONObject();
                String hospInfoUrl = baseUrl + admDtlInfoService + getDtlInfo + encodeServiceKey + ykihoUri + item.getYkiho() ;
                HospDetailBody hospInfoData = openApiDataUtil.getHospData(hospInfoUrl, item.getYkiho(), cacheTtl);
                HospDetailItem data = hospInfoData.getItems().getItem();
                dataSet.put("telno",item.getTelno());
                dataSet.put("addr",item.getAddr());
                dataSet.put("hospitalName",item.getYadmNm());
                dataSet.put("xPos",item.getXPos());
                dataSet.put("yPos",item.getYPos());
                if(data!=null){
                    dataSet.put("noTrmtHoli",data.getNoTrmtHoli());
                    dataSet.put("noTrmtSun",data.getNoTrmtSun());
                    dataSet.put("weekday",calenderAndGetTrmtUtil.getStartByWeekday(data));
                }
                result.put(item.getYkiho(),dataSet);
            }
        }else{
            result.put("result","No data");
        }
        // List의 정보 마다 상세정보 불러오기
        return result.toMap();
    }


    /**
     외부 API에서 병원 상세 정보를 가져와서 모델에 추가하고, hospitalDetail 템플릿을 렌더링한다.
     @param ykiho 데이터를 담을 모델 객체
     @return Map 템플릿을 렌더링한 결과
     */
    @GetMapping("/search/detail")
    public Map getHospDetail(@RequestParam(defaultValue="") String ykiho){
        JSONObject result = new JSONObject();
        if(ykiho.isEmpty()){
            return result.put("result","fail").toMap();
        }
        String url = baseUrl + admDtlInfoService + getDgsbjtInfo + encodeServiceKey + ykihoUri + ykiho;
        System.out.println(url);
        List<HospSubItem> hospListData = openApiDataUtil.getHospSubData(url, ykiho);
        if(hospListData != null){
            result.put("result","success");
            result.put("data",hospListData);
        }else{
            result.put("result","fail");
        }
        return result.toMap();
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
