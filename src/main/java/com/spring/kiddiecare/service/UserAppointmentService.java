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

    /**
     * 사용자측 병원 예약 정보 수정 서비스
     * @param request 세션 로그인 계정 확인용 WebRequest 객체
     * @param appoDto 업데이트 할 예약 정보를 담고있는 Dto
     * @return 업데이트 결과를 담은 JSON 객체
     */
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
        // 기존 예약 시간대 예약 슬롯 +1
        timeSlotsLimitService.plusEnable(oldTimeslotNo);
        // 수정된 예약 시간대 예약 슬롯 -1
        timeSlotsLimitService.minusEnable(appoDto.getTimeSlotNo());

        return jsonObject.put("update", "success");
    }

    /**
     * 사용자측 병원 예약 취소 서비스
     * @param appoNo 예약번호
     * @param request 세션 로그인 계정 확인용 WebRequest 객체
     * @return 예약 취소 결과를 담은 JSON 객체
     */
    @Transactional
    public JSONObject appoCancel(String appoNo, WebRequest request){
        JSONObject jsonObject = new JSONObject();
        String log = (String) request.getAttribute("log", WebRequest.SCOPE_SESSION);

        Optional<User> foundUser = userRepository.findUserById(log);
        if(foundUser.isEmpty()){ return jsonObject.put("cancel", "fail");}
        User user = foundUser.get();

        // 로그인 유저와 예약자 일치 확인
        Optional<Appointment> foundAppo = appoRepository.findAppointmentByNo(appoNo);
        if(foundAppo.isEmpty()){return jsonObject.put("cancel", "fail");}
        Appointment appo = foundAppo.get();

        if(user.getNo() != appo.getGuardian()){return jsonObject.put("cancel", "fail");}

        // 예약 취소
        appo.setAppoStatus(2);
        timeSlotsLimitService.plusEnable(appo.getTimeSlotNo());
        return jsonObject.put("cancel", "success");
    }

    // 예약 완전 삭제 - 사용하지 말것
    @Transactional
    public JSONObject appoDelete(String appoNo, WebRequest request){
        JSONObject jsonObject = new JSONObject();
        String log = (String) request.getAttribute("log", WebRequest.SCOPE_SESSION);

        Optional<User> foundUser = userRepository.findUserById(log);
        if(foundUser.isEmpty()){ return jsonObject.put("delete", "fail");}
        User user = foundUser.get();

        // 로그인 유저와 예약자 일치 확인
        Optional<Appointment> foundAppo = appoRepository.findAppointmentByNo(appoNo);
        if(foundAppo.isEmpty()){return jsonObject.put("delete", "fail");}
        Appointment appo = foundAppo.get();

        if(user.getNo() != appo.getGuardian()){return jsonObject.put("delete", "fail");}

        // 예약 삭제
        timeSlotsLimitService.plusEnable(appo.getTimeSlotNo());
        appoRepository.deleteAppointmentByNo(appoNo);
        return jsonObject.put("delete", "success");
    }
}