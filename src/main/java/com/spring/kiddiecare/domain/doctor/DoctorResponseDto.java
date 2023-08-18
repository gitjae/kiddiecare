package com.spring.kiddiecare.domain.doctor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class DoctorResponseDto {
    private Long no;

    private String ykiho;

    private String doctorName;

    private int doctorAverageTimeOfCare;

    private byte doctorStatus;
}
