package com.spring.kiddiecare.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter // 둘다 게터세터 줘야함
@Getter
@ConfigurationProperties("external.api") //config @ConfigurationPropertiesScan 이걸 Application에 넣어줘야한다.
public class OpenApiProperty {
    private boolean enabled;
    private OpenApi openApi = new OpenApi();

    @Setter
    @Getter
    class OpenApi{
        private String encode;
        private String decode;
    }
}
