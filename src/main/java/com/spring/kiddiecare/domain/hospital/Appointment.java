package com.spring.kiddiecare.domain.hospital;

import com.spring.kiddiecare.util.Timestamp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name="hospital_appointment")
public class Appointment extends Timestamp {

    @Id
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


    public Appointment(AppoRequestDto appoDto) {
        this.no = appoDto.getNo();
        this.patientId = appoDto.getPatientId();
        this.guardian = appoDto.getGuardian();
        this.timeSlotNo = appoDto.getTimeSlotNo();
        this.symptom = appoDto.getSymptom();
        this.note = appoDto.getNote();
        this.appoStatus = appoDto.getAppoStatus();
    }


    public void update(AppoRequestDto appoDto){
        if(appoDto.getTimeSlotNo() != 0){
            this.timeSlotNo = appoDto.getTimeSlotNo();
        }

        if(appoDto.getAppoStatus() != 0){
            this.appoStatus = appoDto.getAppoStatus();
        }

        if(appoDto.getSymptom() != null){
            this.symptom = appoDto.getSymptom();
        }

        if(appoDto.getNote() != null){
            this.note = appoDto.getNote();
        }
    }
}
