package com.spring.kiddiecare.domain.userAppointment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAppointmentDto {

    private Long userId;
    private Long childId;
    private String ykiho;
}