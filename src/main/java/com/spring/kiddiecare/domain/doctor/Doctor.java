package com.spring.kiddiecare.domain.doctor;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
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

    @Column(nullable = true)
    private String doctorImageUrl;

    public Doctor(DoctorResponseDto doctorDto){
        this.no = 0L;
        this.ykiho = doctorDto.getYkiho();
        this.doctorName = doctorDto.getDoctorName();
        this.doctorAverageTimeOfCare = doctorDto.getDoctorAverageTimeOfCare();
        this.doctorStatus = doctorDto.getDoctorStatus();
        this.doctorImageUrl = doctorDto.getDoctorImageUrl();
    }

    public void Update(DoctorResponseDto doctorDto){
        this.doctorName = doctorDto.getDoctorName();
        this.doctorAverageTimeOfCare = doctorDto.getDoctorAverageTimeOfCare();
        this.doctorStatus = doctorDto.getDoctorStatus();
        this.doctorImageUrl = doctorDto.getDoctorImageUrl();
    }
}

