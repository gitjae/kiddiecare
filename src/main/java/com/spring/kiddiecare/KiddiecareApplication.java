package com.spring.kiddiecare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

//@EnableScheduling
@ConfigurationPropertiesScan
@EnableJpaAuditing
@SpringBootApplication
@EnableCaching
public class KiddiecareApplication {

    // war 파일 만들때 생성
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
        return builder.sources(KiddiecareApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(KiddiecareApplication.class, args);
    }

}
