package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.hospital.Hospital;
import com.spring.kiddiecare.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @GetMapping("/hospitalInfo")
    public String hospitalInfo(Model model) {
        model.addAttribute("hospitals", hospitalService.findAllHospitals());
        return "hospitalInfo";
    }

    @GetMapping("/hospitalName/{ykiho}")
    @ResponseBody
    public Hospital hospitalName(@PathVariable String ykiho) {
        return hospitalService.findHospitalByYkiho(ykiho);
    }

}
