package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.user.User;
import com.spring.kiddiecare.domain.user.UserRepository;
import com.spring.kiddiecare.util.CalenderAndGetTrmtUtil;
import com.spring.kiddiecare.util.OpenApiDataUtil;
import com.spring.kiddiecare.util.hospInfo.HospDetailBody;
import com.spring.kiddiecare.util.hospInfo.HospDetailItem;
import com.spring.kiddiecare.util.hospbasis.HospBasisBody;
import com.spring.kiddiecare.util.hospbasis.HospBasisItem;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Duration;
import java.util.ArrayList;
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
    private String HospList = "getHospBasisList";
    private String getDtlInfo = "getDtlInfo2";
    private String getDgsbjtInfo = "getDgsbjtInfo2?";
    private String getTrnsprtInfo  = "getTrnsprtInfo2?";

    private String pageNo = "&pageNo=";
    private String yadmNm = "&yadmNm=";
    private String ykiho = "&ykiho=";
    private String xPos = "&xPos=";
    private String yPos = "&yPos=";
    private String radius = "&radius=500";

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
    public Map getHospList(Model model, @RequestParam(defaultValue="") String keyword,
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
        Duration cacheTtl = Duration.ofMinutes(1);
        HospBasisBody hospListData = openApiDataUtil.getHospList(url, uri, cacheTtl);

        if(hospListData != null){
            result.put("result","success");
            for(HospBasisItem item : hospListData.getItems()){
                JSONObject dataSet = new JSONObject();
                String hospInfoUrl = baseUrl + admDtlInfoService + getDtlInfo + encodeServiceKey + ykiho + item.getYkiho() ;
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
    public Map home(WebRequest request, @RequestParam(defaultValue="1") String requestPageNo){
        String log = (String) request.getAttribute("log", WebRequest.SCOPE_SESSION);

        //String log = "apple";

        JSONObject jsonObject = new JSONObject();

        Optional<User> foundUser = userRepository.findUserById(log);
        if(foundUser.isPresent()){
            User user = foundUser.get();

            String dgsbjtCd = "&dgsbjtCd=11";

            String uri = pageNo + requestPageNo + dgsbjtCd + xPos + user.getXpos() + yPos + user.getYpos() + radius;

            String url = baseUrl + hospInfoService + HospList + encodeServiceKey + uri;
            Duration cacheTtl = Duration.ofMinutes(1);
            HospBasisBody hospListData = openApiDataUtil.getHospList(url, uri, cacheTtl);

            if(hospListData != null){
                jsonObject.put("result","success");
                jsonObject.put("centerX", user.getXpos());
                jsonObject.put("centerY", user.getYpos());
                ArrayList<JSONObject> list = new ArrayList<>();
                for(HospBasisItem item : hospListData.getItems()) {
                    JSONObject dataSet = new JSONObject();
                    String hospInfoUrl = baseUrl + admDtlInfoService + getDtlInfo + encodeServiceKey + ykiho + item.getYkiho();
                    HospDetailBody hospInfoData = openApiDataUtil.getHospData(hospInfoUrl, item.getYkiho(), cacheTtl);
                    HospDetailItem data = hospInfoData.getItems().getItem();
                    dataSet.put("telno", item.getTelno());
                    dataSet.put("addr", item.getAddr());
                    dataSet.put("hospitalName", item.getYadmNm());
                    dataSet.put("xPos", item.getXPos());
                    dataSet.put("yPos", item.getYPos());
                    if (data != null) {
                        dataSet.put("noTrmtHoli", data.getNoTrmtHoli());
                        dataSet.put("noTrmtSun", data.getNoTrmtSun());
                        dataSet.put("weekday", calenderAndGetTrmtUtil.getStartByWeekday(data));
                    }
                    //jsonObject.put(item.getYkiho(), dataSet);
                    list.add(dataSet);
                }
                jsonObject.put("list", list);
            } else {
                jsonObject.put("result", "No Data");
            }
        } else {
            jsonObject.put("result", "Can't Found User Addr");
        }

        return jsonObject.toMap();
    }

}
