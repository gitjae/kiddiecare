package com.spring.kiddiecare.domain.timeSlotsLimit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Time;

@Setter
@Getter
@NoArgsConstructor
public class TimeSlotsLimitRequestDto {
    private int doctorNo;
    private String weekday;
    private String date;
    private int time;
    private int count;
    private int max;
    private int block;
    private int enable;
    private String ykiho;


    @Override
    public String toString() {
        return "TimeSlotsLimitRequestDto{" +
                "doctorNo='" + doctorNo + '\'' +
                ", weekday='" + weekday + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", count='" + count + '\'' +
                ", max='" + max + '\'' +
                ", block='" + block + '\'' +
                ", enable='" + enable + '\'' +
                ", ykiho='" + ykiho + '\'' +
                '}';
    }
}
