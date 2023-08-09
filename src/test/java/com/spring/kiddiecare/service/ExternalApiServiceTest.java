package com.spring.kiddiecare.service;

import com.spring.kiddiecare.util.ApiResponse;
import org.json.JSONObject;
import org.json.XML;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ValueOperations;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExternalApiServiceTest {


    @Autowired
    ExternalApiService externalApiService;
    @Test
    public void fetchData() {
    }

    @Test
    public void fetchData2() {
        String decode = "gq1gsTk3VT1leb+dQUeQCCrKESSBu1f1syDaVYECgLkctflrUfzPFASgoFPU6JQZR6w62FQQwmarazOesgihkA==";
        String encode = "gq1gsTk3VT1leb%2BdQUeQCCrKESSBu1f1syDaVYECgLkctflrUfzPFASgoFPU6JQZR6w62FQQwmarazOesgihkA%3D%3D";

        String x = "128.5493894";
        String y = "35.9348614";
        String r = "100";
        String xpos = "&xPos=" + x;
        String ypos = "&yPos=" + y;

        String radius = "&radius=" + r;

        String url = "https://apis.data.go.kr/B551182/hospInfoServicev2/getHospBasisList?ServiceKey="
                + decode + xpos + ypos + radius;

        Duration cacheTtl = Duration.ofMinutes(1);

        ApiResponse result = externalApiService.fetchData2(url, cacheTtl);


    }
}