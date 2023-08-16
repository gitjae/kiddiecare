package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.hospitalInfo.HospData;
import com.spring.kiddiecare.domain.searchApi.SearchRequestDto;
import com.spring.kiddiecare.domain.user.User;
import com.spring.kiddiecare.domain.user.UserRepository;
import com.spring.kiddiecare.util.CalenderAndGetTrmtUtil;
import com.spring.kiddiecare.util.OpenApiDataUtil;
import com.spring.kiddiecare.openApi.hospbasis.HospBasisItem;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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


    private Optional<HospData> getHospSubByYkiho(String ykiho){
        String hospSubUrl = baseUrl + admDtlInfoService + getDgsbjtInfo + encodeServiceKey + "&ykiho=" + ykiho;
        System.out.println("hospSubUrl "+hospSubUrl);
        return Optional.ofNullable(openApiDataUtil.getHospSubData(hospSubUrl, ykiho));
    }

    private Optional<HospData> getHospDetailByHospData(String ykiho, String yadmNm){
        String hospDetailUrl = baseUrl + admDtlInfoService + getDtlInfo + encodeServiceKey + "&ykiho=" + ykiho;
        System.out.println("hospDetailUrl "+hospDetailUrl);
        return Optional.ofNullable(openApiDataUtil.getHospData(hospDetailUrl, yadmNm));
    }

    private Optional<HospData> getHospListByKeyword(String query){
        String hospListUrl = baseUrl + hospInfoService + HospList + encodeServiceKey + query;
        System.out.println("HospList "+hospListUrl);
        return Optional.ofNullable(openApiDataUtil.getHospList(hospListUrl, query));
    }

    private Map getHospDetailByHospBasis(List<HospBasisItem> items){
        JSONObject result = new JSONObject();
        for(HospBasisItem item : items){
            JSONObject dataSet = new JSONObject();
            Optional<HospData> hospDetail = getHospDetailByHospData(item.getYkiho(),item.getYadmNm());
            if (hospDetail.isPresent()){
                dataSet.put("ykiho",item.getYkiho());
                dataSet.put("telno",item.getTelno());
                dataSet.put("addr",item.getAddr());
                dataSet.put("hospitalName",item.getYadmNm());
                dataSet.put("xPos",item.getXPos());
                dataSet.put("yPos",item.getYPos());
                if(hospDetail.get().getHospDetailData() !=null){
                    dataSet.put("noTrmtHoli",hospDetail.get().getHospDetailData().getNoTrmtHoli());
                    dataSet.put("noTrmtSun",hospDetail.get().getHospDetailData().getNoTrmtHoli());
                    dataSet.put("weekday",calenderAndGetTrmtUtil.getStartByWeekday(hospDetail.get().getHospDetailData()));
                }
            }
            result.put(item.getYadmNm(),dataSet);
        }
        return result.toMap();
    }

    private String getQueryParamBySearchDto(SearchRequestDto searchDto){
        Set<String> keys = searchDto.toParameterMap().keySet();
        String param = "";

        for (String key : keys) {
            String getValue = searchDto.toParameterMap().get(key).toString();
            String paramName = "&"+key+"=";
            if(key.equals("yadmNm")){
                getValue = getEncodeString(getValue);
            }
            param += paramName + getValue;
        }
        return param;
    }

    private String getEncodeString(String getValue){
        try {
            getValue = URLEncoder.encode(getValue, "UTF-8");
        }catch (UnsupportedEncodingException e){
            System.out.println(e);
        }
        return getValue;
    }

    /**
     외부 API 에서 병원 정보를 가져 와서 .
     @param searchDto 렌더링할 데이터를 담을 모델 객체
     @return externalData 템플릿을 렌더링한 결과
     */
    @GetMapping("search/hospList")
    public Map getHospList(@ModelAttribute SearchRequestDto searchDto){
        JSONObject result = new JSONObject();
        // queryParam 받아오기
        String query = getQueryParamBySearchDto(searchDto);
        // openAPI 요청
        Optional<HospData> hospData = getHospListByKeyword(query);
        if(hospData.isPresent()){
            result.put("result","success");
            result.put("data",getHospDetailByHospBasis(hospData.get().getHospListData()));
        }else{
            result.put("result","fail");
        }
        return result.toMap();
    }

    @GetMapping("search/hospList/check")
    public Map getCheckHospList(@ModelAttribute SearchRequestDto searchDto){
        JSONObject result = new JSONObject();
        // queryParam 받아오기
        String query = getQueryParamBySearchDto(searchDto);
        System.out.println("query"+query);
        // openAPI 요청
        Optional<HospData> hospData = getHospListByKeyword(query);
        if(hospData.isPresent()){
            result.put("result","success");
            result.put("data",hospData.get().getHospListData());
        }else{
            System.out.println("f");
            result.put("result","fail");
        }
        return result.toMap();
    }

    @GetMapping("search/hospList/addr")
    public Map getHospListByMyLoc(@ModelAttribute SearchRequestDto searchDto, WebRequest request){

        JSONObject result = new JSONObject();
        String query = getQueryParamBySearchDto(searchDto);
        String log = (String) request.getAttribute("log", WebRequest.SCOPE_SESSION);

        Optional<String> session = Optional.ofNullable(log);
        System.out.println("session" + session);
        if(session.isPresent()){
            Optional<User> userInfo = userRepository.findUserById(session.get());
            if(userInfo.isPresent()){
                String userXpos = "&xPos="+userInfo.get().getXpos();
                String userYpos = "&yPos="+userInfo.get().getYpos();
                query += userXpos+userYpos;
            }
        } else {
            String userXpos = "&xPos=126.97691582974053";
            String userYpos = "&yPos=37.57260376169445";
            query += userXpos+userYpos;
        }
        System.out.println("query:"+query);

        Optional<HospData> hospListData = getHospListByKeyword(query);
        if(hospListData.isPresent()){
            result.put("result","success");
            result.put("data",getHospDetailByHospBasis(hospListData.get().getHospListData()));
        }else{
            result.put("result","fail");
        }
        return result.toMap();
    }


    /**
     외부 API에서 병원 상세 정보를 가져와서 모델에 추가하고, hospitalDetail 템플릿을 렌더링한다.
     @param yadmNm 데이터를 담을 모델 객체
     @return Map 템플릿을 렌더링한 결과
     */
    @GetMapping("/search/detail")
    public Map getHospDetail(@RequestParam(defaultValue="")String yadmNm){
        JSONObject result = new JSONObject();
        System.out.println("yadmNm:"+yadmNm);
        if(yadmNm.isEmpty()){
            return result.put("result","fail").toMap();
        }

        HospData total = new HospData();
        String encodeYadmNm = getEncodeString(yadmNm);

        // openAPI HospList 요청
        String query = "&yadmNm=" + encodeYadmNm;
        Optional<HospData> hospListData = getHospListByKeyword(query);

        System.out.println("hospListData:"+hospListData);
        if(hospListData.isPresent()){
            List<HospBasisItem> HospList = hospListData.get().getHospListData();
            String ykiho = "";
            for(HospBasisItem item : HospList){
                if(item.getYadmNm().equals(yadmNm)){
                    ykiho = item.getYkiho();
                    total.setHospBasisData(item);
                }
            }
            Optional<HospData> hospDetail = getHospDetailByHospData(ykiho,yadmNm);
            Optional<HospData> hospSub = getHospSubByYkiho(ykiho);
            hospDetail.ifPresent(hospData -> total.setHospDetailData(hospData.getHospDetailData()));
            hospSub.ifPresent(hospData -> total.setHospSubData(hospData.getHospSubData()));
            result.put("result","success");
            result.put("data", total);
        }else{
            result.put("result","fail");
        }
        return result.toMap();
    }
}
