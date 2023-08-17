package com.spring.kiddiecare.config;

import com.spring.kiddiecare.controller.EchoHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Bean
    public EchoHandler echoHandler() {
        return new EchoHandler();
    }

//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(echoHandler(), "/echo-ws").addInterceptors(new HttpSessionHandshakeInterceptor());
//        registry.addHandler(echoHandler(), "/echo-ws").withSockJS();
//    }

//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(echoHandler(), "/echo-ws")
//                .setAllowedOrigins("http://localhost:8082")
//                .withSockJS();
//    }
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(echoHandler(), "/echo-ws")
                .addInterceptors(new HttpSessionHandshakeInterceptor()) // 세션 정보 전달
                .withSockJS();
    }
}
