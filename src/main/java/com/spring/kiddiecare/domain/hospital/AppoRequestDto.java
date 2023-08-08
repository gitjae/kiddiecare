package com.spring.kiddiecare.domain.hospital;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.sql.Timestamp;

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
}
