package com.spring.kiddiecare.controller;


import com.spring.kiddiecare.domain.hospital.AppoRepository;
import com.spring.kiddiecare.domain.hospital.AppoRequestDto;
import com.spring.kiddiecare.domain.hospital.Appointment;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin/appo")
public class AppointmentController {

    private final AppoRepository appoRepository;

//    private final AppoRepository appoRepository;
//    @PostMapping("add")
//    public Map addAppo(@ModelAttribute AppoRequestDto appoDto) {
//        Appointment appo = new Appointment(appoDto);
//        JSONObject json = new JSONObject();
//        System.out.println(appo);
////        appoRepository.save(appo);
//        json.put("result", "success");
//
//        return json.toMap();
//
//    }

    @PostMapping(value = "write", consumes = {"multipart/form-data"})
    public Map wrtie(@ModelAttribute AppoRequestDto appoDto) {
        JSONObject json = new JSONObject();

        appoDto.setAppoNo(1);
        appoDto.setId(1);
        appoDto.setUsersNo(1);
        appoDto.setTimeNo(1);
        appoDto.setSymptom("");

        System.out.println(appoDto);
        if(appoDto != null) {
            Appointment appo = new Appointment(appoDto);
            appoRepository.save(appo);

            json.put("write", "success");
        } else {
            json.put("write", "fail");
        }

        return json.toMap();
    }


}
