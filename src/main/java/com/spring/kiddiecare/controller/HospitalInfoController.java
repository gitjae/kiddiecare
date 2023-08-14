package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.searchApi.SearchRequestDto;
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
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Duration;
import java.util.*;

@RequiredArgsConstructor
@RestController
public class HospitalInfoController {
    private final UserRepository userRepository;
    private final OpenApiDataUtil openApiDataUtil;
    private final CalenderAndGetTrmtUtil calenderAndGetTrmtUtil;

    private final String baseUrl = "https://apis.data.go.kr/B551182/";
    private final String hospInfoService="hospInfoServicev2/";
    private final String admDtlInfoService="MadmDtlInfoService2/";
    private final String HospList = "getHospBasisList?";
    private final String getDtlInfo = "getDtlInfo2?";
    private final String getDgsbjtInfo = "getDgsbjtInfo2?";
    private final String getTrnsprtInfo  = "getTrnsprtInfo2?";


    @Value("${external.api.decode}")
    private String decodeServiceKey;
    @Value("${external.api.encode}")
    private String encodeServiceKey;


    private void getHospSubByHospBasis(HospBasisBody hospListData){
        for(HospBasisItem item : hospListData.getItems()){
            String hospInfoUrl = baseUrl + admDtlInfoService + getDgsbjtInfo + encodeServiceKey + "&ykiho=" + item.getYkiho();
            HospBasisItem hospInfoData = openApiDataUtil.getHospSubData(hospInfoUrl, item.getYadmNm());

        }
    }
    private Map getHospDetailByHospBasis(HospBasisBody hospListData){
        JSONObject result = new JSONObject();
        ArrayList<JSONObject> list = new ArrayList<>();
        for(HospBasisItem item : hospListData.getItems()){
            JSONObject dataSet = new JSONObject();
            String hospInfoUrl = baseUrl + admDtlInfoService + getDtlInfo + encodeServiceKey + "&ykiho=" + item.getYkiho() ;
            System.out.println("URL확인 "+hospInfoUrl);
            HospBasisItem hospInfoData = openApiDataUtil.getHospData(hospInfoUrl, item);
            System.out.println("정보 불러오기 "+ hospInfoData);
            dataSet.put("ykiho",hospInfoData.getYkiho());
            dataSet.put("telno",hospInfoData.getTelno());
            dataSet.put("addr",hospInfoData.getAddr());
            dataSet.put("hospitalName",hospInfoData.getYadmNm());
            dataSet.put("xPos",hospInfoData.getXPos());
            dataSet.put("yPos",hospInfoData.getYPos());
            if(hospInfoData.getHospDetail() !=null){
                dataSet.put("noTrmtHoli",hospInfoData.getHospDetail().getNoTrmtHoli());
                dataSet.put("noTrmtSun",hospInfoData.getHospDetail().getNoTrmtHoli());
                dataSet.put("weekday",calenderAndGetTrmtUtil.getStartByWeekday(hospInfoData.getHospDetail()));
            }
            list.add(dataSet);
            //result.put(item.getYadmNm(),dataSet);
        }
        result.put("list",list);
        return result.toMap();
    }

    private String getQueryParamBySearchDto(SearchRequestDto searchDto){
        Set<String> keys = searchDto.toParameterMap().keySet();
        String param = "";

        for (String key : keys) {
            String getValue = searchDto.toParameterMap().get(key).toString();
            String paramName = "&"+key+"=";
            if(key.equals("keyword")){
                try {
                    getValue = URLEncoder.encode(getValue, "UTF-8");
                }catch (UnsupportedEncodingException e){
                    System.out.println(e);
                }
            }
            param += paramName + getValue;
        }
        return param;
    }
    /**
     외부 API에서 병원 정보를 가져와서 모델에 추가하고, externalData 템플릿을 렌더링한다.
     @param searchDto 렌더링할 데이터를 담을 모델 객체
     @return externalData 템플릿을 렌더링한 결과
     */
    @GetMapping("search/hospList")
    public Map getHospList(@ModelAttribute SearchRequestDto searchDto){
        JSONObject result = new JSONObject();
        // queryParam 받아오기
        String query = getQueryParamBySearchDto(searchDto);
        System.out.println(query);
        // hospList 불러오기
        String url = baseUrl + hospInfoService + HospList + encodeServiceKey + query;
        // openAPI 요청
        Optional<HospBasisBody> hospListData = Optional.ofNullable(openApiDataUtil.getHospList(url, query));
        System.out.println(hospListData);
        if(hospListData.isPresent()){
            result.put("result","success");
            result.put("data",getHospDetailByHospBasis(hospListData.get()));
            return result.toMap();
        }
        return result.put("result","fail").toMap();
    }

    @PostMapping("search/hospList/addr")
    public Map getHospListByMyLoc(@ModelAttribute SearchRequestDto searchDto, WebRequest request){
        JSONObject result = new JSONObject();

        String query = getQueryParamBySearchDto(searchDto);
        Optional<String> session = Optional.ofNullable(request.getAttribute("log", WebRequest.SCOPE_SESSION).toString());
        if(session.isPresent()){
            Optional<User> userInfo = userRepository.findUserById(session.get());
            if(userInfo.isPresent()){
                String userXpos = "&xPos="+userInfo.get().getXpos();
                String userYpos = "&yPos="+userInfo.get().getYpos();
                result.put("centerX", userInfo.get().getXpos());
                result.put("centerY", userInfo.get().getYpos());
                query += userXpos+userYpos;
            }
        }
        System.out.println(query);

        String url = baseUrl + hospInfoService + HospList + encodeServiceKey + query;
        Optional<HospBasisBody> hospListData = Optional.ofNullable(openApiDataUtil.getHospList(url, query));
        if(hospListData.isPresent()){
            result.put("result","success");
            result.put("data",getHospDetailByHospBasis(hospListData.get()));
            return result.toMap();
        }
        return result.put("result","fail").toMap();
    }


    /**
     외부 API에서 병원 상세 정보를 가져와서 모델에 추가하고, hospitalDetail 템플릿을 렌더링한다.
     @param ykiho 데이터를 담을 모델 객체
     @return Map 템플릿을 렌더링한 결과
     */
//    @GetMapping("/search/detail")
//    public Map getHospDetail(@RequestParam(defaultValue="") String yadmNm){
//        JSONObject result = new JSONObject();
//        if(yadmNm.isEmpty()){
//            return result.put("result","fail").toMap();
//        }
//
//        String query = "&yadmNm=" + yadmNm;
//        String url = baseUrl + hospInfoService + HospList + encodeServiceKey + query;
//        // openAPI 요청
//
//        //
//        Optional<HospBasisBody> hospListData = Optional.ofNullable(openApiDataUtil.getHospList(url, query));
//        if(hospListData.isPresent()){
//            result.put("result","success");
//            result.put("data",getHospDetailByHospBasis(hospListData.get()));
//        }
//
//
//        url = baseUrl + admDtlInfoService + getDgsbjtInfo + encodeServiceKey + "&ykiho=" + yadmNm;
//        Optional<HospBasisItem> hospData = Optional.ofNullable(openApiDataUtil.getHospSubData(url, yadmNm));
//        if(hospData.isPresent()){
//            result.put("result","success");
//            result.put("data",getHospDetailByYadmNm(hospListData.get()));
//        }
//        if(hospData.isPresent()){
//            result.put("result","success");
//            result.put("data",hospData);
//        }else{
//            result.put("result","fail");
//        }
//        return result.toMap();
//    }

//    @GetMapping("/home")
//    public Map home(WebRequest request, @RequestParam(defaultValue="1") String requestPageNo){
//        String log = (String) request.getAttribute("log", WebRequest.SCOPE_SESSION);
//
//        //String log = "apple";
//
//        JSONObject jsonObject = new JSONObject();
//
//        Optional<User> foundUser = userRepository.findUserById(log);
//        if(foundUser.isPresent()){
//            User user = foundUser.get();
//
//            String dgsbjtCd = "&dgsbjtCd=11";
//
//            String uri = pageNo + requestPageNo + dgsbjtCd + xPos + user.getXpos() + yPos + user.getYpos() + radius;
//
//            String url = baseUrl + hospInfoService + HospList + encodeServiceKey + uri;
//            Duration cacheTtl = Duration.ofMinutes(1);
//            HospBasisBody hospListData = openApiDataUtil.getHospList(url, uri, cacheTtl);
//
//            /*if(hospListData != null){
//                jsonObject.put("result","success");
//                jsonObject.put("centerX", user.getXpos());
//                jsonObject.put("centerY", user.getYpos());
//                ArrayList<JSONObject> list = new ArrayList<>();
//                for(HospBasisItem item : hospListData.getItems()) {
//                    JSONObject dataSet = new JSONObject();
//                    String hospInfoUrl = baseUrl + admDtlInfoService + getDtlInfo + encodeServiceKey + ykihoUri + item.getYkiho();
//                    HospDetailBody hospInfoData = openApiDataUtil.getHospData(hospInfoUrl, item.getYkiho(), cacheTtl);
//                    HospDetailItem data = hospInfoData.getItems().getItem();
//                    dataSet.put("telno", item.getTelno());
//                    dataSet.put("addr", item.getAddr());
//                    dataSet.put("hospitalName", item.getYadmNm());
//                    dataSet.put("xPos", item.getXPos());
//                    dataSet.put("yPos", item.getYPos());
//                    if (data != null) {
//                        dataSet.put("noTrmtHoli", data.getNoTrmtHoli());
//                        dataSet.put("noTrmtSun", data.getNoTrmtSun());
//                        dataSet.put("weekday", calenderAndGetTrmtUtil.getStartByWeekday(data));
//                    }
//                    //jsonObject.put(item.getYkiho(), dataSet);
//                    list.add(dataSet);
//                }
//                jsonObject.put("list", list);
//            } else {
//                jsonObject.put("result", "No Data");
//            }*/
//            jsonObject = makeResponse(hospListData, cacheTtl);
//            jsonObject.put("centerX", user.getXpos());
//            jsonObject.put("centerY", user.getYpos());
//        } else {
//            jsonObject.put("result", "Can't Found User Addr");
//        }
//
//        return jsonObject.toMap();
//    }
//
//    @GetMapping("/location")
//    public Map location(WebRequest request, @RequestParam(defaultValue="1") String requestPageNo
//            , @RequestParam String x, @RequestParam String y){
//        //JSONObject jsonObject = new JSONObject();
//
//        String dgsbjtCd = "&dgsbjtCd=11";
//
//        String uri = pageNo + requestPageNo + dgsbjtCd + xPos + x + yPos + y + radius;
//
//        String url = baseUrl + hospInfoService + HospList + encodeServiceKey + uri;
//        Duration cacheTtl = Duration.ofMinutes(1);
//        HospBasisBody hospListData = openApiDataUtil.getHospList2(url, uri, cacheTtl);
//
//        /*if(hospListData != null){
//            jsonObject.put("result","success");
//
//            ArrayList<JSONObject> list = new ArrayList<>();
//            for(HospBasisItem item : hospListData.getItems()) {
//                JSONObject dataSet = new JSONObject();
//                String hospInfoUrl = baseUrl + admDtlInfoService + getDtlInfo + encodeServiceKey + ykihoUri + item.getYkiho();
//                HospDetailBody hospInfoData = openApiDataUtil.getHospData(hospInfoUrl, item.getYkiho(), cacheTtl);
//                HospDetailItem data = hospInfoData.getItems().getItem();
//                dataSet.put("telno", item.getTelno());
//                dataSet.put("addr", item.getAddr());
//                dataSet.put("hospitalName", item.getYadmNm());
//                dataSet.put("xPos", item.getXPos());
//                dataSet.put("yPos", item.getYPos());
//                if (data != null) {
//                    dataSet.put("noTrmtHoli", data.getNoTrmtHoli());
//                    dataSet.put("noTrmtSun", data.getNoTrmtSun());
//                    dataSet.put("weekday", calenderAndGetTrmtUtil.getStartByWeekday(data));
//                }
//                //jsonObject.put(item.getYkiho(), dataSet);
//                list.add(dataSet);
//            }
//            jsonObject.put("list", list);
//        } else {
//            jsonObject.put("result", "No Data");
//        }*/
//        JSONObject jsonObject = makeResponse(hospListData, cacheTtl);
//
//        return jsonObject.toMap();
//    }
//
//    @GetMapping("hospital/detail")
//    public Map getHospitalInfo(@RequestParam(defaultValue="") String ykiho){
//        JSONObject jsonObject = new JSONObject();
//
//        HospBasisItem item = openApiDataUtil.getHospBasisItem(ykiho);
//
//        if(item !=null){
//            jsonObject.put("result","success");
//            jsonObject.put("item", item);
//        } else {
//            jsonObject.put("result","fail");
//        }
//
//        /*
//        // hospList 불러오기
//        String uri = ykihoUri + ykiho;
//        String url = baseUrl + hospInfoService + HospList + encodeServiceKey + uri;
//        //System.out.println("dd "+url);
//        Duration cacheTtl = Duration.ofMinutes(3);
//        HospBasisBody hospListData = openApiDataUtil.getHospList(url, uri, cacheTtl);
//
//        //System.out.println("info:"+hospListData);
//
//        jsonObject.put("result", "success");
//        jsonObject = makeResponse(hospListData, cacheTtl);
//        */
//
//        return jsonObject.toMap();
//    }
//
//    public JSONObject makeResponse(HospBasisBody hospListData, Duration cacheTtl){
//        JSONObject jsonObject = new JSONObject();
//
//        if(hospListData != null){
//            jsonObject.put("result","success");
//
//            ArrayList<JSONObject> list = new ArrayList<>();
//            for(HospBasisItem item : hospListData.getItems()) {
//                JSONObject dataSet = new JSONObject();
//                String hospInfoUrl = baseUrl + admDtlInfoService + getDtlInfo + encodeServiceKey + ykihoUri + item.getYkiho();
//                HospDetailBody hospInfoData = openApiDataUtil.getHospData(hospInfoUrl, item.getYkiho(), cacheTtl);
//                HospDetailItem data = hospInfoData.getItems().getItem();
//                dataSet.put("ykiho", item.getYkiho());
//                dataSet.put("telno", item.getTelno());
//                dataSet.put("addr", item.getAddr());
//                dataSet.put("hospitalName", item.getYadmNm());
//                dataSet.put("xPos", item.getXPos());
//                dataSet.put("yPos", item.getYPos());
//                if (data != null) {
//                    dataSet.put("noTrmtHoli", data.getNoTrmtHoli());
//                    dataSet.put("noTrmtSun", data.getNoTrmtSun());
//                    dataSet.put("weekday", calenderAndGetTrmtUtil.getStartByWeekday(data));
//                }
//                //jsonObject.put(item.getYkiho(), dataSet);
//                list.add(dataSet);
//            }
//            jsonObject.put("list", list);
//        } else {
//            jsonObject.put("result", "No Data");
//        }
//
//        return jsonObject;
//    }
}
