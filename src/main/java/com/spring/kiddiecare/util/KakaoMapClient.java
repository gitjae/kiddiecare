package com.spring.kiddiecare.util;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.HttpURLConnection;



@Component
public class KakaoMapClient {

    private final RestTemplate restTemplate;

    @Value("${kakao.restapi.key}")
    private String API_KEY;

    public KakaoMapClient(){
        restTemplate = new RestTemplate();
    }

    /**
     * 회원가입시 입력한 주소를 경도, 위도값으로 변환
     * @param addr 회원가입시 입력한 주소
     * @return 경도, 위도 정보가 담긴 string
     */
    public String geocodeAddress(String addr){
        String url = "https://dapi.kakao.com/v2/local/search/address?query=" + addr;

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + API_KEY);

        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        JSONObject json = new JSONObject(response.getBody());

        JSONArray documents = json.getJSONArray("documents");
        if (documents.length() > 0) {
            JSONObject document = documents.getJSONObject(0);
            String x = document.getString("x");
            String y = document.getString("y");
            return x + "/" + y;
        }

        return response.getBody();
    }
}
