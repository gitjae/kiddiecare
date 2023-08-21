package com.spring.kiddiecare.domain.alarm;

import lombok.*;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AlarmReqeustDto {
    private long alarm_no;
    private String hosp_ykiho;
    private String user_id;
    private String alarm_text;
}
