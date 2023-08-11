package com.spring.kiddiecare.service;

import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimit;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TimeSlotsLimitService {
    @Autowired
    private TimeSlotsLimitRepository timeSlotsLimitRepository;

    public List<TimeSlotsLimit> findTimeSlotsByYkiho(String ykiho) {
        return timeSlotsLimitRepository.findByYkiho(ykiho);
    }

    public List<TimeSlotsLimit> findTimeSlotsByYkihoAndDate(String ykiho, Date date) {
        return timeSlotsLimitRepository.findByYkihoAndDate(ykiho, date);
    }
}

