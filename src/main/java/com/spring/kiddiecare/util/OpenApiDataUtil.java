package com.spring.kiddiecare.util;

import com.spring.kiddiecare.util.hospInfo.HospDetailInfoResponse;
import com.spring.kiddiecare.util.hospInfo.HospDetailInfoResponseJson;
import com.spring.kiddiecare.util.hospbasis.HospBasisBody;
import com.spring.kiddiecare.util.hospbasis.HospBasisItem;
import com.spring.kiddiecare.util.hospbasis.HospBasisResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

@Component
public class OpenApiDataUtil {

    private final RestTemplate restTemplate;
    private final RedisTemplate redisTemplate;
    private ValueOperations<String, HospBasisBody> valueOps;
    @Autowired // inner 클래스 위에 있는 변수들을 생성해준다. @Autowired는 원래는 쓰지 않고 @RequiredArgsConstructor 사용
    public OpenApiDataUtil(RedisTemplate redisTemplate) {
        this.restTemplate = new RestTemplate();
        this.redisTemplate = redisTemplate;
        this.valueOps = redisTemplate.opsForValue();
    }

    public HospBasisBody hosInfoList(String url, String uri, Duration cacheTtl) {
        HospBasisBody cachedData = valueOps.get(uri);
        if (cachedData != null) {
            return cachedData;
        }

        HospBasisBody resultBody = null;
        try {
            URI reqeusturl = new URI(url);
            System.out.println(uri);
            HospBasisResponse data = restTemplate.getForObject(reqeusturl, HospBasisResponse.class);
            resultBody = data.getBody();
            System.out.println(resultBody);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (resultBody != null) {
            valueOps.set(uri, resultBody, cacheTtl);
            return resultBody;
        }

        return null;
    }

    public HospBasisBody hosInfoDetail(String url, String uri, Duration cacheTtl) {
        HospBasisBody cachedData = valueOps.get(uri);
        if (cachedData != null) {
            return cachedData;
        }

        HospBasisBody result = null;
        try {
            URI reqeusturl = new URI(url);
            System.out.println(uri);
            HospDetailInfoResponse data = restTemplate.getForObject(reqeusturl , HospDetailInfoResponse.class);
            System.out.println(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (result != null) {
            valueOps.set(uri, result, cacheTtl);
            return result;
        }

        return null;
    }

//    public HospBasisResponse fetchDataClass(String url, Duration cacheTtl) {
//        HospBasisResponse cachedData = valueOps.get(url);
//        HospBasisResponse data = null;
//
//        if (cachedData != null) {
//            return cachedData;
//        }
//
//        try {
//            URI uri = new URI(url);
//            data = restTemplate.getForObject(uri, HospBasisResponse.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if (data != null) {
//            valueOps.set(url, data, cacheTtl);
//            return data;
//        }
//
//        return null;
//    }


}
