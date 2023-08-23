package com.spring.kiddiecare.domain.hospital;

import com.spring.kiddiecare.domain.hospitalAdmin.AdminRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Hospital {

    @Id
    private String ykiho;

    @Column(nullable = false)
    private String hospitalName;

    @Column
    private String hospitalIntro;

    @Column
    private String hospitalAnnouncement;

    //@Column
    //private int hospitalMaxSlots;

    //@Column
    //private int reservesSlots;

    public Hospital(AdminRequestDto adminDto){
        this.ykiho = adminDto.getYkiho();
        this.hospitalName = adminDto.getHospitalName();
        this.hospitalIntro = adminDto.getHospitalName() + "입니다!";
    }
}
