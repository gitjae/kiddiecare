package com.spring.kiddiecare.service;

import com.spring.kiddiecare.util.ApiResponse;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
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


        String data = null;
        try {
            URI uri = new URI(url);
            data = restTemplate.getForObject(uri, String.class);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        System.out.println("data:" + data);


        if(data != null && !data.contains("SERVICE ERROR")){

            valueOps.set(url, data, cacheTtl);
            return new ApiResponse(data, "API");
        }

        return new ApiResponse(data, "error");
    }


    public ApiResponse fetchData2(String url, Duration cacheTtl){
        StringBuffer result = new StringBuffer();

        String jsonPrintString = null;

        System.out.println("fe2 in");

        String cachedDate = valueOps.get(url);
        if(cachedDate != null){
            return new ApiResponse(cachedDate, "cache");
        }

        System.out.println("fe2 no cache");

        try {
            URL url1 = new URL(url);

            HttpURLConnection urlConnection = (HttpURLConnection) url1.openConnection();
            urlConnection.connect();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
            String returnLine;
            while ((returnLine = bufferedReader.readLine()) != null){
                result.append(returnLine);
            }

            System.out.println("fe2 api return");

            JSONObject jsonObject = XML.toJSONObject(result.toString());
            jsonPrintString = jsonObject.toString();

            System.out.println("fe2 json convert");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(jsonPrintString != null && !jsonPrintString.contains("SERVICE ERROR")){
            valueOps.set(url, jsonPrintString, cacheTtl);
            return new ApiResponse(jsonPrintString, "API");
        }

        return new ApiResponse(jsonPrintString, "error");
    }
}
