package com.spring.kiddiecare.domain.likeHospital;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "likeHospital")
@Entity
public class LikeHospital {
    @Id
    private int no;
    private int userNo;
    private String ykiho;

    public LikeHospital(LikeHospitalRequestDto likeRequestDto) {
        this.no = likeRequestDto.getNo();
        this.userNo = likeRequestDto.getUserNo();
        this.ykiho = likeRequestDto.getYkiho();
    }
}
