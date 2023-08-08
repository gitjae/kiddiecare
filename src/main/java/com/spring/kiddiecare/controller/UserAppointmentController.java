package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.hospital.Hospital;
import com.spring.kiddiecare.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/appointment")
public class UserAppointmentController {

    @Autowired
    private HospitalService hospitalService;

    // 파라미터로 병원코드 받아서 단일 병원 정보 나타내기
    @GetMapping("/hospitalDetail")
    public String showReservePage(@RequestParam("ykiho") String ykiho, Model model) {
        Hospital hospital = hospitalService.findHospitalByYkiho(ykiho);
        model.addAttribute("hospital", hospital);
        return "hospitalDetail";
    }
}