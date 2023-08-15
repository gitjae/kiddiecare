package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimit;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimitRepository;
import com.spring.kiddiecare.util.DateParsor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.ls.LSInput;

import java.util.*;

@RestController
@SessionAttributes({"log"})
public class TimeSlotsController {
    @Autowired
    private TimeSlotsLimitRepository timeSlotsLimitRepository;

    @GetMapping("/timeSlotsDateGetByYkiho")
    public Map timeSlotsDateGetByYkiho(@RequestParam String ykiho, @RequestParam Date date){
        JSONObject jsonObject = new JSONObject();
        List<TimeSlotsLimit> slots = timeSlotsLimitRepository.findTimeSlotsLimitByYkihoAndDate(ykiho, date);
        jsonObject.put("slots", slots);
        return jsonObject.toMap();
    }

    @GetMapping("/getTimeSlots")
    public List<TimeSlotsLimit> getTimeSlots(@RequestParam String ykiho, String date, Long doctorNo) {
        Date parseDate = DateParsor.parse(date);
        return timeSlotsLimitRepository.findTimeSlotsLimitByYkihoAndDateAndDoctorNo(ykiho, parseDate, doctorNo);

    }

}

