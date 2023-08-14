package com.spring.kiddiecare.domain.hospital;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppoRequestDto {

    private String no;

    // children
    private int patientId;

    // users(보호자)
    private int guardian;

    // time_slots_limit
    private int timeSlotNo;
    private String symptom;
    private String note;
    private int appoStatus;

    @Override
    public String toString() {
        return "AppoRequestDto{" +
                "no=" + no +
                ", patientId=" + patientId +
                ", guardian=" + guardian +
                ", timeSlotNo=" + timeSlotNo +
                ", symptom='" + symptom + '\'' +
                ", note='" + note + '\'' +
                ", appoStatus=" + appoStatus +
                '}';
    }
}
