package com.spring.kiddiecare.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter // 둘다 게터세터 줘야함
@Getter
@ConfigurationProperties("cloudfront")
public class CloudFrontProperty {
    private boolean enabled;
    private CloudFrontProperty.CloudFront cloudFront = new CloudFrontProperty.CloudFront();

    @Setter
    @Getter
    class CloudFront{
        private String url;
    }
}
