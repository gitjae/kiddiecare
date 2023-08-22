package com.spring.kiddiecare.domain.alarm;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository <Alarm,Long>{
    public Alarm findByUserId(String userId);
}
