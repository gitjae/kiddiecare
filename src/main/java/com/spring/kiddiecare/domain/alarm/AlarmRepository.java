package com.spring.kiddiecare.domain.alarm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmRepository extends JpaRepository <Alarm,Long>{
    public Page<Alarm> findAllByUserNo(int userNo, Pageable pageable);
}
