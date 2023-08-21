package com.spring.kiddiecare.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties("naver.sms")
public class NaverApiProperty {
    private NaverApi naverApi = new NaverApi();
    class NaverApi{
        private String accessKey;
        private String secretKey;
        private String serviceId;
        private String senderNumber;
    }
}
