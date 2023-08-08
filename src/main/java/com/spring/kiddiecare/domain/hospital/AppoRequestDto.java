package com.spring.kiddiecare.domain.hospital;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppoRequestDto {
    private int appoNo;

    // children
    private int id;

    // users(보호자)
    private int usersNo;

    // time_slots_limit
    private int timeNo;
    private String symptom;
    private String note;
    private int appoStatus;
//    private Timestamp createdTime;
//    private Timestamp modifiedTime;


    @Override
    public String toString() {
        return "AppoRequestDto{" +
                "appoNo=" + appoNo +
                ", id=" + id +
                ", usersNo=" + usersNo +
                ", timeNo=" + timeNo +
                ", symptom='" + symptom + '\'' +
                ", note='" + note + '\'' +
                ", appoStatus=" + appoStatus +
                '}';
    }
}
