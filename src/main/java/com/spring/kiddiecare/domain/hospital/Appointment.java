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
    

//    @Column(name = "created_time")
//    private Timestamp createdTime;
//    @Column(name = "modified_time")
//    private Timestamp modifiedTime;


    public Appointment(int appoNo, int id, int usersNo, int timeNo, String symptom, String note, int appoStatus) {
        this.appoNo = appoNo;
        this.id = id;
        this.usersNo = usersNo;
        this.timeNo = timeNo;
        this.symptom = symptom;
        this.note = note;
        this.appoStatus = appoStatus;
    }

    public Appointment(AppoRequestDto appoDto) {
        this.appoNo = appoDto.getAppoNo();
        this.id = appoDto.getId();
        this.usersNo = appoDto.getUsersNo();
        this.timeNo = appoDto.getTimeNo();
        this.symptom = appoDto.getSymptom();
        this.note = appoDto.getNote();
        this.appoStatus = appoDto.getAppoStatus();
    }

//    public Appointment(AppoRequestDto appoDto) {
//    }

//    @PrePersist
//    public void prePersist() {
//        this.createdTime = new Timestamp(System.currentTimeMillis());
//    }
}
