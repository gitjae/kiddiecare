package com.spring.kiddiecare.util.shift;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Time;

@Setter
@Getter
@NoArgsConstructor
@Table(name = "shift")
@Entity
public class Shift {
    @Id
    private int no;
    private int doctorNo;
    private String weekDay;
    private Time startTime;
    private Time endTime;
    private Time lunchStartTime;
    private Time lunchEndTime;
    private Time dinnerStartTime;
    private Time dinnerEndTime;
    private boolean active;
    private int max;

    public Shift(ShiftRequestDto shiftDto){
        this.doctorNo = shiftDto.getDoctorNo();
        this.weekDay = shiftDto.getWeekDay();
        this.startTime = shiftDto.getStartTime();
        this.endTime = shiftDto.getEndTime();
        this.lunchStartTime = shiftDto.getLunchStartTime();
        this.lunchEndTime = shiftDto.getLunchEndTime();
        this.dinnerStartTime = shiftDto.getDinnerStartTime();
        this.dinnerEndTime = shiftDto.getDinnerEndTime();
        this.max = shiftDto.getMax() == null ? 0 : shiftDto.getMax();
    }

    public void update(ShiftRequestDto shiftDto){
        if(shiftDto.getStartTime() != null){
            this.startTime = shiftDto.getStartTime();
        }
        if(shiftDto.getEndTime() != null){
            this.endTime = shiftDto.getEndTime();
        }
        if(shiftDto.getLunchStartTime() != null){
            this.lunchStartTime = shiftDto.getLunchStartTime();
        }
        if(shiftDto.getLunchEndTime() != null){
            this.lunchEndTime = shiftDto.getLunchEndTime();
        }
        if(shiftDto.getDinnerStartTime() != null){
            this.dinnerStartTime = shiftDto.getDinnerStartTime();
        }
        if(shiftDto.getDinnerEndTime() != null){
            this.dinnerEndTime = shiftDto.getDinnerEndTime();
        }
        if(shiftDto.getMax() != null){
            this.max = shiftDto.getMax();
        }
    }
}
