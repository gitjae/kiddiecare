package com.spring.kiddiecare.domain.webSocket;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WebSocketRequest {
    private String target;
    private String  content;
    private String  type;
    private String  url;
}
