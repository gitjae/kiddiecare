package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimit;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimitRepository;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimitRequestDto;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin/appo")
public class AppointmentController {

//    private final AppoRepository appoRepository;
    private final TimeSlotsLimitRepository timeSlotsLimitRepository;

    @Transactional
    @PostMapping(value="appo-add", consumes = {"application/json"})
    public Map appo_add(@RequestBody List<TimeSlotsLimitRequestDto> list) {

        JSONObject json = new JSONObject();

        System.out.println(list.get(0).getDoctorNo());

        for (TimeSlotsLimitRequestDto dto : list) {
            System.out.println(dto);
            TimeSlotsLimit timeSlotsLimit = new TimeSlotsLimit(dto);
//            timeSlotsLimit.setCount(timeSlotsLimit.getCount()+1);
            timeSlotsLimitRepository.save(timeSlotsLimit);
            json.put("result", timeSlotsLimit.getTime());
        }
        return json.toMap();
    }
}
