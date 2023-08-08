package com.spring.kiddiecare.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class MainController {
    @GetMapping("admin/appointment")
    public String hospitalReservationForm() {
        return "hospitalAppointmentForm";
    }
}
