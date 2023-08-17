package com.spring.kiddiecare.service;

import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimit;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Service
public class TimeSlotsLimitService {
    private final TimeSlotsLimitRepository timeSlotsLimitRepository;

    // 예약정보 수정 시 기존 시간의 time_slots_limit 테이블의 enable은 줄어들고,
    // 변경한 시간의 enable은 감소
    @Transactional
    public void plusEnable(int timeSlotNo) {
        // time_slots_limit -> no -> enable +1, count -1
        TimeSlotsLimit timeSlotsLimit = timeSlotsLimitRepository.findById(timeSlotNo)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + timeSlotNo));
        int newCount = timeSlotsLimit.getCount() -1;
        timeSlotsLimit.setCount(newCount);
        timeSlotsLimit.setEnable(timeSlotsLimit.getMax() - (newCount + timeSlotsLimit.getBlock()));

        timeSlotsLimitRepository.save(timeSlotsLimit);
    }

    @Transactional
    public void minusEnable(int timeSlotNo) {
        // time_slots_limit -> no -> enable -1, count +1
        TimeSlotsLimit timeSlotsLimit = timeSlotsLimitRepository.findById(timeSlotNo)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + timeSlotNo));
        int newCount = timeSlotsLimit.getCount() +1;
        timeSlotsLimit.setCount(newCount);
        timeSlotsLimit.setEnable(timeSlotsLimit.getMax() - (newCount + timeSlotsLimit.getBlock()));

        timeSlotsLimitRepository.save(timeSlotsLimit);
    }
}

