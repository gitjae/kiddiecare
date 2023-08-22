package com.spring.kiddiecare.util.shift;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;

@Setter
@Getter
@NoArgsConstructor
public class ShiftResponseDto {
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
}
