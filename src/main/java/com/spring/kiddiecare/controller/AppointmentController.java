package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.doctor.DoctorRepository;
import com.spring.kiddiecare.domain.hospital.*;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimit;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimitRepository;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimitRequestDto;
import com.spring.kiddiecare.service.HospitalAppointmentService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin/appo")
public class AppointmentController {

    private final AppoRepository appoRepository;
    private final TimeSlotsLimitRepository timeSlotsLimitRepository;
    private final DoctorRepository doctorRepository;
    private final HospitalAppointmentService hospitalAppointmentService;
    private final AppoResponseRepository appoResponseRepository;
    private final AppoAdminDetailRepository appoAdminDetailRepository;

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

    @GetMapping("/getAppoDetails")
    public List<AppoResponseDto> getAppoDetails(@RequestParam int timeSlotNo){
        System.out.println(timeSlotNo);
        System.out.println(appoResponseRepository.findAllBySlotNo(timeSlotNo));
        return appoResponseRepository.findAllBySlotNo(timeSlotNo);
    }

    @GetMapping("/getAppoUserDetail")
    public AppoAdminDetailDto getAppoAdminDetail(@RequestParam String hospAppoNo) {
        return appoAdminDetailRepository.findByHospAppoNo(hospAppoNo);
    }
}
