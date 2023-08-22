package com.spring.kiddiecare.controller;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("alarm")
public class AlarmController {
    @PostMapping("check")
    public Map getAlarmByDB(){
        JSONObject response = new JSONObject();

        return response.toMap();
    }
}
