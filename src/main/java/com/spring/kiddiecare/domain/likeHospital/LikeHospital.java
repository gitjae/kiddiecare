package com.spring.kiddiecare.domain.likeHospital;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "like_hospital")
@Entity
public class LikeHospital {
    @Id
    private int no;
    private int userNo;
    private String ykiho;
    private String hospitalName;
    private String sgguCd;

    public LikeHospital(LikeHospitalRequestDto likeRequestDto) {
        this.no = likeRequestDto.getNo();
        this.userNo = likeRequestDto.getUserNo();
        //this.ykiho = likeRequestDto.getYkiho();
        this.hospitalName = likeRequestDto.getHospitalName();
        this.sgguCd = likeRequestDto.getSgguCd();
    }
}
