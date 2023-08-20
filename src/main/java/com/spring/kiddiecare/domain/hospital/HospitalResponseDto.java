package com.spring.kiddiecare.domain.hospital;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Setter
@Getter
@NoArgsConstructor
public class HospitalResponseDto {
    private String ykiho;
    private String hospitalName;
    private String hospitalIntro;
    private String hospitalAnnouncement;
    private int hospitalMaxSlots;
    private int reservesSlots;

    // ykiho와 hospitalName을 초기화하는 생성자
    public HospitalResponseDto(String ykiho, String hospitalName) {
        this.ykiho = ykiho;
        this.hospitalName = hospitalName;
    }
}
