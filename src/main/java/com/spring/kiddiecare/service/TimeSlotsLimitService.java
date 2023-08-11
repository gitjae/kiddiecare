package com.spring.kiddiecare.service;

import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimit;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeSlotsLimitService {
    @Autowired
    private TimeSlotsLimitRepository timeSlotsLimitRepository;

    // 병원코드에 따라 진료정보 검색
    public List<TimeSlotsLimit> findTimeSlotsByYkiho(String ykiho) {
        return timeSlotsLimitRepository.findByYkiho(ykiho);
    }
}
