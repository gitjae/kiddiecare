package com.spring.kiddiecare.domain.hospital;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="hospital_appointment")
public class Appointment {
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
    @Column(name = "created_time")
    private Timestamp createdTime;
    @Column(name = "modified_time")
    private Timestamp modifiedTime;


    public Appointment(int appoNo, int id, int usersNo, int timeNo, String symptom, String note, int appoStatus, Timestamp createdTime, Timestamp modifiedTime) {
        this.appoNo = appoNo;
        this.id = id;
        this.usersNo = usersNo;
        this.timeNo = timeNo;
        this.symptom = symptom;
        this.note = note;
        this.appoStatus = appoStatus;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
    }

    public Appointment(AppoRequestDto appoDto) {
    }

//    @PrePersist
//    public void prePersist() {
//        this.createdTime = new Timestamp(System.currentTimeMillis());
//    }
}
