package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimit;
import com.spring.kiddiecare.service.TimeSlotsLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Date;
import java.util.List;

@RestController
@SessionAttributes({"log"})
public class TimeSlotsViewController {
    @Autowired
    private TimeSlotsLimitService timeSlotsLimitService;

    public List<TimeSlotsLimit> getTimeSlotsByDateAndYkiho(
            @RequestParam("ykiho") String ykiho,
            @RequestParam("date") @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {

        return timeSlotsLimitService.findTimeSlotsByYkihoAndDate(ykiho, date);
    }
}
