package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.service.TimeSlotsLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"log"})
public class TimeSlotsController {

    @Autowired
    private TimeSlotsLimitService timeSlotsLimitService;
}

