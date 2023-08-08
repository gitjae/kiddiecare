package com.spring.kiddiecare.util;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
@RequiredArgsConstructor
public class CacheConfig {

    private final RedisConnectionFactory connectionFactory;


}
