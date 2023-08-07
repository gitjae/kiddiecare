package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.hospitalInfo.HospitalInfo;
import com.spring.kiddiecare.service.ExternalApiService;
import com.spring.kiddiecare.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Duration;

@Controller
public class HospitalInfoController {
    @Autowired
    private ExternalApiService externalApiService;

    private String decode = "gq1gsTk3VT1leb+dQUeQCCrKESSBu1f1syDaVYECgLkctflrUfzPFASgoFPU6JQZR6w62FQQwmarazOesgihkA==";
    private String encode = "gq1gsTk3VT1leb%2BdQUeQCCrKESSBu1f1syDaVYECgLkctflrUfzPFASgoFPU6JQZR6w62FQQwmarazOesgihkA%3D%3D";

    private String x = "128.5493894";
    private String y = "35.9348614";
    private String r = "100";
    private String xpos = "&xPos=" + x;
    private String ypos = "&yPos=" + y;

    private String radius = "&radius=" + r;

    @GetMapping("/fetch")
    public String fetchExternalData(Model model){
        String externalApiUrl =
                "https://apis.data.go.kr/B551182/hospInfoServicev2/getHospBasisList?ServiceKey=" + encode + xpos + ypos + radius;

        Duration cacheTtl = Duration.ofMinutes(1);

        ApiResponse apiResponse = externalApiService.fetchData2(externalApiUrl, cacheTtl);
        model.addAttribute("apiResponse", apiResponse);

        return "externalData";
    }
}
