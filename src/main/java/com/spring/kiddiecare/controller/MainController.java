package com.spring.kiddiecare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("join")
    public String join(){
        return "join";
    }
}
