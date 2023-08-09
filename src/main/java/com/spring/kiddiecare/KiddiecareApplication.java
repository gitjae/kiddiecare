package com.spring.kiddiecare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@EnableCaching
public class KiddiecareApplication {

    public static void main(String[] args) {
        SpringApplication.run(KiddiecareApplication.class, args);
    }

}
