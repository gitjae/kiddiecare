package com.spring.kiddiecare.domain.doctor;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Doctor {

    @Id
    private Long no;

    @Column(nullable = false, length = 80)
    private String ykiho;

    @Column(nullable = false, length = 20)
    private String doctorName;

    @Column(nullable = false)
    private int doctorAverageTimeOfCare;

    @Column(nullable = false, columnDefinition = "TINYINT DEFAULT 0") // default 값 설정
    private byte doctorStatus;
}

