package com.spring.kiddiecare.domain.alarm;

import lombok.*;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AlarmReqeustDto {
    private long alarmNo;
    private String hospYkiho;
//    private String userId;
    private int userNo;
    private String alarmText;
}
