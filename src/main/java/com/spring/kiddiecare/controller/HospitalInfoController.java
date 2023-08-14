package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.hospitalInfo.HospData;
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


    private Optional<HospData> getHospSubByYkiho(String ykiho){
        String hospInfoUrl = baseUrl + admDtlInfoService + getDgsbjtInfo + encodeServiceKey + "&ykiho=" + ykiho;
        Optional<HospData> hospSub = Optional.ofNullable(openApiDataUtil.getHospSubData(hospInfoUrl, ykiho));
        return hospSub;
    }

    private Optional<HospData> getHospDetailByHospData(String ykiho, String yadmNm){
        String hospInfoUrl = baseUrl + admDtlInfoService + getDtlInfo + encodeServiceKey + "&ykiho=" + ykiho;
        System.out.println("URL확인 "+hospInfoUrl);
        Optional<HospData> hospInfoData = Optional.ofNullable(openApiDataUtil.getHospData(hospInfoUrl, yadmNm));
        System.out.println("정보 불러오기 "+ hospInfoData);
        return hospInfoData;
    }

    private Optional<HospData> getHospListByKeyword(String query){
        String url = baseUrl + hospInfoService + HospList + encodeServiceKey + query;
        // openAPI 요청
        Optional<HospData> hospList = Optional.ofNullable(openApiDataUtil.getHospList(url, query));
        return hospList;
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
                query += userXpos+userYpos;
            }
        }

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
    public Map getHospDetail(@RequestParam(defaultValue="") String yadmNm){
        JSONObject result = new JSONObject();
        if(yadmNm.isEmpty()){
            return result.put("result","fail").toMap();
        }

        // openAPI 요청
        String query = "&yadmNm=" + yadmNm;
        Optional<HospData> hospListData = getHospListByKeyword(query);
        // 저장을 따로 해줘야할 필요가 있음 ㅇㅇ
        if(hospListData.isPresent()){
            List<HospBasisItem> HospList = hospListData.get().getHospListData();
            String ykiho = "";
            for(HospBasisItem item : HospList){
                if(item.getYadmNm().equals(yadmNm)){
                    ykiho = item.getYkiho();
                }
            }
            result.put("result","success");
            result.put("data",getHospSubByYkiho(ykiho));
        }else{
            result.put("result","fail");
        }
        return result.toMap();
    }
}
