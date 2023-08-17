package com.spring.kiddiecare.domain.timeSlotsLimit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Setter
@Getter
@NoArgsConstructor
public class TimeSlotsLimitResponseDto {
    private int no;
    private long doctorNo;
    private String weekday;
    private Date date;
    private Time time;
    private int count;
    private int max;
    private int block;
    private int enable;
    private String ykiho;       // hospital table FK 추가
}
