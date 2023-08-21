package com.spring.kiddiecare.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties("kakao.restapi")
public class KakaoApiProperty {
    private boolean enabled;
    private KakaoApiProperty.KakaoApi kakaoApi = new KakaoApi();

    @Setter
    @Getter
    class KakaoApi{
        private String key;
        private String appkey;
    }
}
