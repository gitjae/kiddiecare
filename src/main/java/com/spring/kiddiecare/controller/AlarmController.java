package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.alarm.Alarm;
import com.spring.kiddiecare.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("alarm")
public class AlarmController {

    private final UserRepository userRepository;

//    @PostMapping("check")
//    public List<Alarm> getAlarmByDB(String id){
//        userRepository.
//    }
}
