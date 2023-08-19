package com.spring.kiddiecare.domain.doctor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "doctor")
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

    public Doctor(DoctorResponseDto doctorDto){
        this.no = 0L;
        this.ykiho = doctorDto.getYkiho();
        this.doctorName = doctorDto.getDoctorName();
        this.doctorAverageTimeOfCare = doctorDto.getDoctorAverageTimeOfCare();
        this.doctorStatus = doctorDto.getDoctorStatus();
    }

    public void Update(DoctorResponseDto doctorDto){
        this.doctorName = doctorDto.getDoctorName();
        this.doctorAverageTimeOfCare = doctorDto.getDoctorAverageTimeOfCare();
        this.doctorStatus = doctorDto.getDoctorStatus();
    }
}

