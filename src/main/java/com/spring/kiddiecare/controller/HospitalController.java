package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @GetMapping("/hospitalInfo")
    public String hospitalInfo(Model model) {
        model.addAttribute("hospitals", hospitalService.findAllHospitals());
        return "hospitalInfo";
    }

}
