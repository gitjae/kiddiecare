package com.spring.kiddiecare.domain.likeHospital;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikeHospitalRequestDto {
    private int no;
    private int userNo;
    private String ykiho;
}
