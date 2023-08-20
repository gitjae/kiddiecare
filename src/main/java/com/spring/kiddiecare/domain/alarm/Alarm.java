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
    private long alarm_no;
    private String hosp_ykiho;
    private String user_id;
    private String alarm_text;

    public Alarm(AlarmReqeustDto alarmDto){
        this.alarm_no = 0L;
        this.hosp_ykiho = alarmDto.getHosp_ykiho();
        this.user_id = alarmDto.getUser_id();
        this.alarm_text = alarmDto.getAlarm_text();
    }
}
