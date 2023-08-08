package com.spring.kiddiecare.domain.UserAppointment;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class UserAppointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no; // 예약 번호

    @Column(nullable = false)
    private Long userId;

    @Column
    private Long childId;

    @Column(nullable = false)
    private String ykiho; // 병원 코드
}
