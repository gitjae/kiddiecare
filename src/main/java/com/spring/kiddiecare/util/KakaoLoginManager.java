package com.spring.kiddiecare.util;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class KakaoLoginManager {

    private RestTemplate restTemplate = new RestTemplate();

    @Value("${kakao.restapi.key}")
    private String KAKAO_REST_API_KEY;

    public String getKAKAO_REST_API_KEY(){
        return KAKAO_REST_API_KEY;
    }

    public String redirect_uri = "http://localhost:8080/login/kakao/callback";


    public String getKakaoAccessToken(String code){
        String access_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";
        System.out.println(code);
        HttpHeaders headers = new HttpHeaders();
        //headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Accept", "application/json");


        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();
        parameters.add("grant_type", "authorization_code");
        System.out.println("kakao api key : " + KAKAO_REST_API_KEY);
        parameters.add("client_id", KAKAO_REST_API_KEY);
        parameters.add("redirect_uri", redirect_uri);
        parameters.add("code", code);

        HttpEntity<MultiValueMap<String, Object>> restRequest = new HttpEntity<>(parameters, headers);

        //ResponseEntity<JSONObject> response = restTemplate.postForEntity(reqURL, restRequest, JSONObject.class);
        ResponseEntity<String> response = restTemplate.postForEntity(reqURL, restRequest, String.class);
        System.out.println(response);
        String responseBody = response.getBody();

        JSONObject jsonObject = new JSONObject(responseBody);

//        JSONObject responseBody = response.getBody();
//        System.out.println(responseBody);
//        access_Token = (String) responseBody.get("access_token");

        access_Token = jsonObject.getString("access_token");

        return access_Token;
    }

    public String getKakaoUserInfo(String accessToken){
        String reqURL = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "bearer " + accessToken);
        headers.set("KakaoAK", KAKAO_REST_API_KEY);

        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();
        JSONArray propertyKeys = new JSONArray();
        propertyKeys.put("kakao_account.email");

        parameters.add("property_keys", propertyKeys.toString());

        HttpEntity<MultiValueMap<String, Object>> restRequest = new HttpEntity<>(parameters, headers);
        ResponseEntity<String> apiResponse = restTemplate.postForEntity(reqURL, restRequest, String.class);
        String responseBody = apiResponse.getBody();
        JSONObject jsonBody = new JSONObject(responseBody);
        JSONObject kakaoAccount = jsonBody.getJSONObject("kakao_account");
        String email = kakaoAccount.getString("email");

        return email;
    }
}
