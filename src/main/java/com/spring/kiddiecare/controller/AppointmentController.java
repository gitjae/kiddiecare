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
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/appo")
public class AppointmentController {

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

}
