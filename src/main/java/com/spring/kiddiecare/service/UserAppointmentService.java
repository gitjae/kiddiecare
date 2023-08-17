package com.spring.kiddiecare.service;

import com.spring.kiddiecare.domain.hospital.*;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimit;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimitRepository;
import com.spring.kiddiecare.domain.user.User;
import com.spring.kiddiecare.domain.user.UserRepository;
import com.spring.kiddiecare.domain.userAppointment.UserAppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserAppointmentService {

    private final UserRepository userRepository;

    private final AppoRepository appoRepository;
    private final AppoResponseObjectRepository appoResponseObjectRepository;
    private final TimeSlotsLimitRepository timeSlotsLimitRepository;
    private final TimeSlotsLimitService timeSlotsLimitService;

    @Transactional
    public JSONObject updateAppo(WebRequest request, AppoRequestDto appoDto){
        JSONObject jsonObject = new JSONObject();
        String log = (String) request.getAttribute("log", WebRequest.SCOPE_SESSION);

        Optional<User> foundUser = userRepository.findUserById(log);
        if(foundUser.isEmpty()){ return jsonObject.put("update", "fail");}
        User user = foundUser.get();

        // 로그인 유저와 예약자 일치 확인
        Optional<Appointment> foundAppo = appoRepository.findAppointmentByNo(appoDto.getNo());
        if(foundAppo.isEmpty()){return jsonObject.put("update", "fail");}
        Appointment appo = foundAppo.get();

        if(user.getNo() != appo.getGuardian()){return jsonObject.put("update", "fail");}

        // 예약 수정
        int oldTimeslotNo = appo.getTimeSlotNo();

        appo.update(appoDto);
        timeSlotsLimitService.plusEnable(oldTimeslotNo);
        timeSlotsLimitService.minusEnable(appoDto.getTimeSlotNo());

        return jsonObject.put("update", "success");
    }
}