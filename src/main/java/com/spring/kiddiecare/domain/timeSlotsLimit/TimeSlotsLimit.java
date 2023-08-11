package com.spring.kiddiecare.domain.timeSlotsLimit;

import com.spring.kiddiecare.util.DateParsor;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="time_slots_limit")
public class TimeSlotsLimit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public TimeSlotsLimit(TimeSlotsLimitRequestDto timeSlotsDto) {
        this.doctorNo = Long.parseLong(timeSlotsDto.getDoctorNo() + "");
        this.weekday = timeSlotsDto.getWeekday();
        this.date = DateParsor.parse(timeSlotsDto.getDate());
        this.time = DateParsor.convertHourToTime(timeSlotsDto.getTime());
        this.count = timeSlotsDto.getCount();
        this.max = timeSlotsDto.getMax();
        this.block = timeSlotsDto.getBlock();
        this.enable = timeSlotsDto.getEnable();
        this.ykiho = timeSlotsDto.getYkiho();       // hospital table FK 추가
    }

    public void setCount(int count) {
        this.count = count;
        this.enable = (this.max - count) < 0 ? 0 : (this.max - count);
    }
}
