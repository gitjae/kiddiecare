package com.spring.kiddiecare.domain.hospital;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponseDto {

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

    public AppointmentResponseDto(Appointment appo){
        this.no = appo.getNo();
        this.patientId = appo.getPatientId();
        this.guardian = appo.getGuardian();
        this.timeSlotNo = appo.getTimeSlotNo();
        this.symptom = appo.getSymptom();
        this.note = appo.getNote();
        this.appoStatus = appo.getAppoStatus();
    }
}
