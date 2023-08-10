package com.spring.kiddiecare.util;

import com.spring.kiddiecare.util.hospbasis.HospBasisBody;
import com.spring.kiddiecare.util.hospbasis.HospBasisItem;
import com.spring.kiddiecare.util.hospbasis.HospBasisResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

@Component
public class OpenApiDataUtil {

    private final RestTemplate restTemplate;
    private final RedisTemplate redisTemplate;
    private ValueOperations<String, HospBasisResponse> valueOps;
    @Autowired
    public OpenApiDataUtil(RedisTemplate redisTemplate) {
        this.restTemplate = new RestTemplate();
        this.redisTemplate = redisTemplate;
        this.valueOps = redisTemplate.opsForValue();
    }

    public ApiResponse fetchData(String url, Duration cacheTtl) {
        HospBasisResponse cachedDate = valueOps.get(url);
        if (cachedDate != null) {
            return new ApiResponse(cachedDate.toString(), "cache");
        }

        HospBasisResponse data = null;

        try {
            URI uri = new URI(url);
            data = restTemplate.getForObject(uri, HospBasisResponse.class);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        System.out.println("data:" + data);

        if (data != null) {
            valueOps.set(url, data, cacheTtl);
            return new ApiResponse(data.toString(), "API");
        }

        return new ApiResponse(data.toString(), "error");
    }


    public HospBasisResponse hosInfoList(String url, Duration cacheTtl) {
        HospBasisResponse cachedData = valueOps.get(url);
        HospBasisResponse data = null;

        if (cachedData != null) {
            return cachedData;
        }

        try {
            URI uri = new URI(url);
            data = restTemplate.getForObject(uri, HospBasisResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("data:" + data);

        if (data != null) {
            valueOps.set(url, data, cacheTtl);
            return data;
        }

        return null;
    }

    public HospBasisResponse fetchDataClass(String url, Duration cacheTtl) {
        HospBasisResponse cachedData = valueOps.get(url);
        HospBasisResponse data = null;

        if (cachedData != null) {
            return cachedData;
        }

        try {
            URI uri = new URI(url);
            data = restTemplate.getForObject(uri, HospBasisResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (data != null) {
            valueOps.set(url, data, cacheTtl);
            return data;
        }

        return null;
    }


}
