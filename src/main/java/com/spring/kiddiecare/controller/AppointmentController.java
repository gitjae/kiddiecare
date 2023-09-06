package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.alarm.Alarm;
import com.spring.kiddiecare.domain.alarm.AlarmRepository;
import com.spring.kiddiecare.domain.alarm.AlarmReqeustDto;
import com.spring.kiddiecare.domain.doctor.DoctorRepository;
import com.spring.kiddiecare.domain.doctor.DoctorResponseDto;
import com.spring.kiddiecare.domain.hospital.*;
import com.spring.kiddiecare.domain.hospitalAdmin.Admin;
import com.spring.kiddiecare.domain.hospitalAdmin.AdminRepository;
import com.spring.kiddiecare.domain.hospitalAdmin.AdminRequestDto;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimit;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimitRepository;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimitRequestDto;
import com.spring.kiddiecare.domain.user.User;
import com.spring.kiddiecare.domain.user.UserRepository;
import com.spring.kiddiecare.service.HospitalAppointmentService;
import com.spring.kiddiecare.service.TimeSlotsLimitService;
import com.spring.kiddiecare.util.SMSSender;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin/appo")
public class AppointmentController {

    private final AppoRepository appoRepository;
    private final TimeSlotsLimitRepository timeSlotsLimitRepository;
    private final AdminRepository adminRepository;
    private final HospitalAppointmentService hospitalAppointmentService;
    private final AppoResponseRepository appoResponseRepository;
    private final AppoAdminDetailRepository appoAdminDetailRepository;
    private final TimeSlotsLimitService timeSlotsLimitService;
    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;
    private final SMSSender smsSender;

    /**
     * 의사의 ykiho,no를 받아 해당 의사의 근무 일을 반환 해준다.
     * @param doctorDto ykiho,no
     * @param request 세션 확인
     * @return dateList 해당 의사가 예약된
     */
    @GetMapping("date-list")
    public Map getAlreadyDateList(@RequestParam String ykiho, @RequestParam long no, WebRequest request){
        JSONObject json = new JSONObject();

        String log = (String) request.getAttribute("log",WebRequest.SCOPE_SESSION);
        if(log == null){
            return json.put("result","fail no session").toMap();
        }

        Optional<Admin> user = Optional.ofNullable(adminRepository.findByAdminId(log));

        if(user.isPresent()) {
            List<TimeSlotsLimit> list = timeSlotsLimitRepository.findDateByYkihoAndDoctorNo(ykiho, no);
            HashMap<Date, Integer> map = new HashMap<>();
            for (TimeSlotsLimit slot : list) {
                map.put(slot.getDate(), map.getOrDefault(slot.getDate(), 0) + 1);
            }
            json.put("result", "success");
            json.put("data", map.keySet().stream().sorted());
        }

        return json.toMap();
    }

    // 최종추가 되는거? 예약 생성됨
    @Transactional
    @PostMapping(value="timeset-add", consumes = {"application/json"})
    public Map timeset_add(@RequestBody List<TimeSlotsLimitRequestDto> list) {

        JSONObject json = new JSONObject();

        for (TimeSlotsLimitRequestDto dto : list) {
            TimeSlotsLimit timeSlotsLimit = new TimeSlotsLimit(dto);
            timeSlotsLimitRepository.save(timeSlotsLimit);
        }
            json.put("result", "success");

        return json.toMap();
    }


    @PostMapping(consumes = {"application/json"})
    public ResponseEntity<Map<String, Boolean>> bookAppointment(@RequestBody AppoRequestDto appoDto) {
        HashMap<String, Boolean> response = new HashMap<>();
        // 난수 생성
        // setter로 난수만 appoDto에 추가했음!
        appoDto.setNo(hospitalAppointmentService.duplCode());
        try {
            Appointment appointment = new Appointment(appoDto);
            appoRepository.save(appointment);
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch(Exception e) {
            response.put("success", false);
            e.printStackTrace();
            return ResponseEntity.status(500).body(response);  // 500 Internal Server Error 반환
        }
    }

//    @GetMapping("/getAppoDetails")
//    public List<Appointment> getAppoDetails(@RequestParam int timeSlotNo) {
//        return appoRepository.findAllByTimeSlotNo(timeSlotNo);
//    }

    @GetMapping("/getAppoDetails")
    public List<AppoResponseDto> getAppoDetails(@RequestParam int timeSlotNo) {
        // 삭제가 아닌 리스트만 가져올 때
//        List<AppoResponseDto> appoList = appoResponseRepository.findAllByTimeSlotNo(timeSlotNo);
//        List<AppoResponseDto> noDeleteList = new ArrayList<>();
//
//        for(AppoResponseDto data : appoList) {
//            // status가 삭제가 아닌 리스트만 가져오기
//            if(data.getAppoStatus() != 2) {
//                noDeleteList.add(data);
//            }
//        }
//        return noDeleteList;

        // 저장된 모든 리스트 가져올 때
        return appoResponseRepository.findAllByTimeSlotNo(timeSlotNo);
    }

    @GetMapping("/getAppoUserDetail")
    public AppoAdminDetailDto getAppoAdminDetail(@RequestParam String hospAppoNo) {
        return appoAdminDetailRepository.findByHospAppoNo(hospAppoNo);
    }

    // 유저 예약상태 변경
    @PutMapping("/modifyAdminAppo/appoStatusChange")
    public Map updateStatus(@RequestParam String appoNo, int status, String hospitalName) {
        // appointmentNo로 해당 appointment객체 반환
        JSONObject json = new JSONObject();
        Appointment appo = appoRepository.findByNo(appoNo);
        AlarmReqeustDto alarm = new AlarmReqeustDto();

        int userNo = 0;
        String userId = "";
        String userName;

        try {
            // hospital_appointment 테이블의 status, timeSlots 테이블의 enable 변경
            hospitalAppointmentService.updateStatus(appoNo, status);
            int timeNo = appo.getTimeSlotNo();
            userNo = appo.getGuardian();

            TimeSlotsLimit timeSlot = timeSlotsLimitRepository.findByNo(timeNo);
            String ykiho = timeSlot.getYkiho();
            // 예약된 시간 포맷
            Time time = timeSlot.getTime();
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            String timeString = timeFormat.format(time);

            String hours = timeString.substring(0, 2); // 첫번째 ':'의 앞 두글자만 추출
            // 예약된 timeslot 날짜 포맷
            Date date = timeSlot.getDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = dateFormat.format(date);

            User user = userRepository.findUserByNo(userNo);
            userName = user.getName();

            userId = user.getId();

            String statusVal = "";
            if (status == 1) {
                statusVal = "예약완료";
            } else if (status == 2) {
                statusVal = "예약취소";
            } else if (status == 3) {
                statusVal = "예약보류";
            } else {
                statusVal = "이용완료";
            }

            String text = "[" + hospitalName + "] " + userName + "님! " + dateString + " " +
                    hours + "시 예약 '" + statusVal + "' 상태로 변경되었습니다.";

            alarm.setAlarmText(text);
            alarm.setHospYkiho(ykiho);
            alarm.setUserNo(userNo);
            alarmRepository.save(new Alarm(alarm));

            // 상태 변경 시 메세지 보내기
            String userPhone = user.getPhone();
            String sendMessage = "[우리동네소아과]\n" +
                    "[" + hospitalName + "]" + userName + "님! " +
                    dateString + " " + hours + "시 예약상태가 " + statusVal +
                    "로 변경되었습니다.";

            Map responseBody = null;
            // 문자 메세지 전송

            responseBody = smsSender.sendLms(userPhone, sendMessage);
            if(responseBody != null) {
                String statusCode = (String) responseBody.get("statusCode");
                if(statusCode.equals("202")){
                    json.put("sms","success");
                }else{
                    return json.put("sms", "fail").toMap();
                }
            }
        } catch (Exception e) {
            return json.put("status", "fail").toMap();
        }
        // 성공 시 userId 반환
        json.put("sId", userId);
        json.put("alarm", alarm);

        return json.toMap();
    }


    // enable 값 변경
    // int status => 3: 변경
    @PutMapping("/modifyAdminAppo/{status}")
    public Map updateAppo(@RequestParam String hospAppoNo, int hour, int timeSlotNo,@PathVariable int status) {
        JSONObject json = new JSONObject();

        if(status == 3) {
            int lastTimeSlotNo;
            // 기존 timeSlot테이블 변경
            lastTimeSlotNo = appoRepository.findByNo(hospAppoNo).getTimeSlotNo();
            timeSlotsLimitService.plusEnable(lastTimeSlotNo);

            // 현재(변경한) timeSlot테이블 변경
            timeSlotsLimitService.minusEnable(timeSlotNo);

            // appointment 테이블 변경
            hospitalAppointmentService.changeTimeSlot(hospAppoNo, timeSlotNo);
            json.put("update", "success");
        }

        return json.toMap();
    }

    // 병원 기준으로 전체 예약정보 받아오기
    @GetMapping("/getAllAppo")
    public List<Appointment> getAllAppo(String hospAppoNo) {
        return appoRepository.findAll();
    }


}
