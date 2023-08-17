package com.spring.kiddiecare.domain.doctor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Setter
@Getter
@NoArgsConstructor
public class DoctorResponseDto {
    private Long no;

    private String ykiho;

    private String doctorName;

    private int doctorAverageTimeOfCare;

    private byte doctorStatus;
}
