package com.spring.kiddiecare.util;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class KakaoLoginManager {

    private RestTemplate restTemplate = new RestTemplate();
    private String KAKAO_REST_API_KEY = "76566ba0dfe292387f057613321b300c";

    public String getKAKAO_REST_API_KEY(){
        return KAKAO_REST_API_KEY;
    }

    public String redirect_uri = "http://localhost:8080";

    public String getKakaoAccessToken(String code){
        String access_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "Content-type: application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();
        parameters.set("grant_type", "authorization_code");
        parameters.set("client_id", KAKAO_REST_API_KEY);
        parameters.set("redirect_uri", redirect_uri);
        parameters.set("code", code);

        HttpEntity<MultiValueMap<String, Object>> restRequest = new HttpEntity<>(parameters, headers);

        ResponseEntity<JSONObject> response = restTemplate.postForEntity(reqURL, restRequest, JSONObject.class);
        JSONObject responseBody = response.getBody();

        access_Token = (String) responseBody.get("access_token");

        return access_Token;
    }

    public JSONObject getKakaoUserInfo(String accessToken){
        String reqURL = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "bearer " + accessToken);
        headers.set("KakaoAK", KAKAO_REST_API_KEY);

        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();
        JSONArray propertyKeys = new JSONArray();
        propertyKeys.put("kakao_account.email");
        propertyKeys.put("kakao_account.gender");
        propertyKeys.put("kakao_account.birthday");

        parameters.add("property_keys", propertyKeys.toString());

        HttpEntity<MultiValueMap<String, Object>> restRequest = new HttpEntity<>(parameters, headers);
        ResponseEntity<JSONObject> apiResponse = restTemplate.postForEntity(reqURL, restRequest, JSONObject.class);
        JSONObject responseBody = apiResponse.getBody();

        return responseBody;
    }
}
