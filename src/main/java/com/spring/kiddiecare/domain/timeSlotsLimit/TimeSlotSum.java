package com.spring.kiddiecare.domain.timeSlotsLimit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Setter
@Getter
@NoArgsConstructor
public class TimeSlotSum {
    private String ykiho;
    private Date date;
    private Time time;
    private int count;
    private int max;
    private int block;
    private int enable;

    public TimeSlotSum(TimeSlotsLimit slot){
        this.ykiho = slot.getYkiho();
        this.date = slot.getDate();
        this.time = slot.getTime();
        this.count = slot.getCount();
        this.max = slot.getMax();
        this.block = slot.getBlock();
        this.enable = slot.getEnable();
    }

    public void add(TimeSlotsLimit slot){
        this.time = slot.getTime();
        this.count += slot.getCount();
        this.max += slot.getMax();
        this.block += slot.getBlock();
        this.enable += slot.getEnable();
    }
}
