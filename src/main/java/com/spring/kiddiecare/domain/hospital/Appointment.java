package com.spring.kiddiecare.domain.hospital;

import com.spring.kiddiecare.util.Timestamp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name="hospital_appointment")
public class Appointment extends Timestamp {
    @Id
    private int no;

    // children
    private int patientId;

    // users(보호자)
    private int guardian;

    // time_slots_limit
    private int timeSlotNo;
    private String symptom;
    private String note;
    private int appoStatus;
    

    public Appointment(int no, int patientId, int guardian, int timeSlotNo, String symptom, String note, int appoStatus) {
        this.no = no;
        this.patientId = patientId;
        this.guardian = guardian;
        this.timeSlotNo = timeSlotNo;
        this.symptom = symptom;
        this.note = note;
        this.appoStatus = appoStatus;
    }

    public Appointment(AppoRequestDto appoDto) {
        this.no = appoDto.getNo();
        this.patientId = appoDto.getPatientId();
        this.guardian = appoDto.getGuardian();
        this.timeSlotNo = appoDto.getTimeSlotNo();
        this.symptom = appoDto.getSymptom();
        this.note = appoDto.getNote();
        this.appoStatus = appoDto.getAppoStatus();
    }

}
