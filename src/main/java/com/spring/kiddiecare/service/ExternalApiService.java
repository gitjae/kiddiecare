package com.spring.kiddiecare.service;

import com.spring.kiddiecare.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Service
public class ExternalApiService {
    private final RestTemplate restTemplate;
    private final StringRedisTemplate redisTemplate;
    private ValueOperations<String, String> valueOps;

    @Autowired
    public ExternalApiService(StringRedisTemplate redisTemplate){
        this.restTemplate = new RestTemplate();
        this.redisTemplate = redisTemplate;
        this.valueOps = redisTemplate.opsForValue();
    }

    public ApiResponse fetchData(String url, Duration cacheTtl){
        String cachedDate = valueOps.get(url);
        if(cachedDate != null){
            return new ApiResponse(cachedDate, "cache");
        }

        String data = restTemplate.getForObject(url, String.class);
        System.out.println("data:" + data);


        if(data != null && !data.contains("SERVICE ERROR")){

            valueOps.set(url, data, cacheTtl);
            return new ApiResponse(data, "API");
        }

        return new ApiResponse(data, "error");
    }
}
