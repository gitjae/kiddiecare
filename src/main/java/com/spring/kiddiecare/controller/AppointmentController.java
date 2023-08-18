package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.doctor.DoctorRepository;
import com.spring.kiddiecare.domain.doctor.DoctorResponseDto;
import com.spring.kiddiecare.domain.hospital.*;
import com.spring.kiddiecare.domain.hospitalAdmin.Admin;
import com.spring.kiddiecare.domain.hospitalAdmin.AdminRepository;
import com.spring.kiddiecare.domain.hospitalAdmin.AdminRequestDto;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimit;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimitRepository;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimitRequestDto;
import com.spring.kiddiecare.service.HospitalAppointmentService;
import com.spring.kiddiecare.service.TimeSlotsLimitService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.sql.Date;
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

        System.out.println(list.get(0).getDoctorNo());

        for (TimeSlotsLimitRequestDto dto : list) {
            System.out.println(dto);
            TimeSlotsLimit timeSlotsLimit = new TimeSlotsLimit(dto);
//            timeSlotsLimit.setCount(timeSlotsLimit.getCount()+1);
            timeSlotsLimitRepository.save(timeSlotsLimit);
        }
            json.put("result", "success");

        return json.toMap();
    }

    @Transactional
    @PostMapping("timeset-create")
    public void timeset_create(@RequestBody Map<String, List<TimeSlotsLimitRequestDto>> data) {
        for (String key : data.keySet()) {
            List<TimeSlotsLimitRequestDto> dataList = data.get(key);
            for (TimeSlotsLimitRequestDto rowData : dataList) {
                String date = rowData.getDate();
                String weekday = rowData.getWeekday();
                int time = rowData.getTime();
                int max = rowData.getMax();
                // max랑 동일하게
                int enable = rowData.getMax();
                rowData.setEnable(enable);
                // 임시
//                rowData.setDoctorNo(2);
//                rowData.setYkiho("JDQ4MTYyMiM1MSMkMSMkMCMkODkkMzgxMzUxIzExIyQxIyQzIyQ3OSQyNjE4MzIjNDEjJDEjJDgjJDgz");

                // 잘 들어와졌는지 테스트
//                System.out.println("date " + date);
//                System.out.println("weekday " + weekday);
//                System.out.println("time " + time);
//                System.out.println("max " + max);
//                System.out.println("enable " + enable);

                TimeSlotsLimit timeSlotsLimit = new TimeSlotsLimit(rowData);
                System.out.println(timeSlotsLimit);
                timeSlotsLimitRepository.save(timeSlotsLimit);
            }
        }
    }


    // 충돌나서 주석처리.
//    @GetMapping("{ykiho}")
//    public List<Doctor> getDoctors(@PathVariable String ykiho) {
//        return doctorRepository.findByYkiho(ykiho);
//    }


//    @PostMapping(value = "appo-add", consumes = {"application/json"})
//    public Map appo_add(@RequestBody AppoRequestDto appoDto) {
//        JSONObject json = new JSONObject();
//
//        int randNumber = Integer.parseInt(createRanNum());
////        String randNumber = createRanNum();
//
//        System.out.println(randNumber);
//
//        appoDto.setNo(randNumber);
//        appoDto.setPatientId(1);
//        appoDto.setGuardian(1);
//        appoDto.setTimeSlotNo(1);
//        appoDto.setSymptom("");
//
//        System.out.println(appoDto);
//        Appointment appo = new Appointment(appoDto);
//        appoRepository.save(appo);
//
//        json.put("add", "success");
//
//        return json.toMap();
//    }

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

//    @GetMapping("/getAppoDetails")
//    public List<AppoResponseDto> getAppoDetails(@RequestParam int timeSlotNo) {
//        return appoResponseRepository.findAllByTimeSlotNo(timeSlotNo);
//    }

    // 수정
    @GetMapping("/getAppoDetails/{page}")
    public List<AppoResponseDto> getAppoDetails(@RequestParam int timeSlotNo, @PathVariable int page, @PageableDefault(size=10) Pageable pageable){
        List<AppoResponseDto> appoList = appoResponseRepository.findAllBySlotNoAndPage(timeSlotNo, pageable.withPage(page-1));
        List<AppoResponseDto> noDeleteList = new ArrayList<>();

        for(AppoResponseDto data : appoList) {
           // status가 삭제가 아닌 리스트만 가져오기
            if(data.getAppoStatus() != 2) {
                noDeleteList.add(data);
            }
        }
        return noDeleteList;
//        System.out.println(appoResponseRepository.findAllBySlotNoAndPage(timeSlotNo, pageable.withPage(page-1)));
//        return appoResponseRepository.findAllBySlotNoAndPage(timeSlotNo, pageable.withPage(page-1));
    }

    @GetMapping("/getAppoUserDetail")
    public AppoAdminDetailDto getAppoAdminDetail(@RequestParam String hospAppoNo) {
        return appoAdminDetailRepository.findByHospAppoNo(hospAppoNo);
    }

    // 유저 예약상태 변경
    @PutMapping("/modifyAdminAppo/appoStatusChange")
    public Map updateStatus(@RequestParam String appoNo, int status) {
//        Appointment appo = appoRepository.findByNo(appoNo);
        JSONObject json = new JSONObject();

        hospitalAppointmentService.updateStatus(appoNo, status);
        json.put("status","update");

        return json.toMap();
    }

    // enable 자동 변경
    // int status => 1: 추가, 2: 삭제, 3: 변경, 4: 보류
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
