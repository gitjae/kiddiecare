package com.spring.kiddiecare.domain.like;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikeRequestDto {
    private int no;
    private int user_no;
    private String ykiho;
}
