package com.spring.kiddiecare.controller;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

@Controller
public class HospitalInfoControllerV1 {
    StringBuffer result = new StringBuffer();

    String jsonPrintString = null;

    private String decode = "gq1gsTk3VT1leb+dQUeQCCrKESSBu1f1syDaVYECgLkctflrUfzPFASgoFPU6JQZR6w62FQQwmarazOesgihkA==";
    private String encode = "gq1gsTk3VT1leb%2BdQUeQCCrKESSBu1f1syDaVYECgLkctflrUfzPFASgoFPU6JQZR6w62FQQwmarazOesgihkA%3D%3D";

    private String x = "128.5493894";
    private String y = "35.9348614";
    private String r = "100";
    private String xpos = "&xPos=" + x;
    private String ypos = "&yPos=" + y;

    private String radius = "&radius=" + r;

    String externalApiUrl =
            "https://apis.data.go.kr/B551182/hospInfoServicev2/getHospBasisList?ServiceKey=" + decode + xpos + ypos + radius;

    @GetMapping("/callApi")
    public String callApi(){
        URL url;

        {
            try {
                url = new URL(externalApiUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
                String returnLine;
                while ((returnLine = bufferedReader.readLine()) != null){
                    result.append(returnLine);
                }

                JSONObject jsonObject = XML.toJSONObject(result.toString());
                jsonPrintString = jsonObject.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return jsonPrintString;
        }

    }
}
