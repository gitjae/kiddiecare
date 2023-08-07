package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.UserAppointment.UserAppointmentDto;
import com.spring.kiddiecare.service.UserAppointment_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment")
public class UserAppointment_Controller {

    @Autowired
    private UserAppointment_Service appointmentService;

    @PostMapping("/book")
    public void bookAppointment(@RequestBody UserAppointmentDto requestDto) {
        appointmentService.book(requestDto);
    }

}
