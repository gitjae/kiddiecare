package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.alarm.Alarm;
import com.spring.kiddiecare.domain.alarm.AlarmRepository;
import com.spring.kiddiecare.domain.user.User;
import com.spring.kiddiecare.domain.user.UserRepository;
import com.spring.kiddiecare.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("alarm")
public class AlarmController {

    private final UserRepository userRepository;
    private final AlarmService alarmService;

    // 알람 목록 5개씩만 표시
    @PostMapping("check")
    public Page<Alarm> getAlarmByDB(@RequestParam String id, @PageableDefault(size=5, sort = "alarmNo", direction = Sort.Direction.DESC) Pageable pageable){
        Optional<User> findUser = userRepository.findUserById(id);
        if(findUser.isPresent()) {
            User user = findUser.get();
            int userNo = user.getNo();
            Page<Alarm> alarmList = alarmService.getAlarm(userNo, pageable);

            return alarmList;
        }

        return null;
    }
}
