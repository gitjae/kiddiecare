package com.spring.kiddiecare.domain.alarm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "alarm")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Alarm {
    @Id
    private long alarmNo;
    private String hospYkiho;
//    private String userId;
    private int userNo;
    private String alarmText;

    public Alarm(AlarmReqeustDto alarmDto){
        this.alarmNo = 0L;
        this.hospYkiho = alarmDto.getHospYkiho();
        this.userNo = alarmDto.getUserNo();
        this.alarmText = alarmDto.getAlarmText();
    }
}
