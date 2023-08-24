package com.spring.kiddiecare.service;

import com.spring.kiddiecare.domain.alarm.Alarm;
import com.spring.kiddiecare.domain.alarm.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AlarmService {
    public final AlarmRepository alarmRepository;

    @Transactional
    public Page<Alarm> getAlarm(int userNo, Pageable pageable) {
        Page<Alarm> alarms = alarmRepository.findAllByUserNo(userNo, pageable) ;

        return alarms;
    }
}
