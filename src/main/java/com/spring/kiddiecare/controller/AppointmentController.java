package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.doctor.Doctor;
import com.spring.kiddiecare.domain.doctor.DoctorRepository;
import com.spring.kiddiecare.domain.hospital.AppoRepository;
import com.spring.kiddiecare.domain.hospital.AppoRequestDto;
import com.spring.kiddiecare.domain.hospital.Appointment;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimit;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimitRepository;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimitRequestDto;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
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


    @GetMapping("{ykiho}")
    public List<Doctor> getDoctors(@PathVariable String ykiho) {
        return doctorRepository.findByYkiho(ykiho);
    }


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

    @PostMapping
    public Map<String, Boolean> bookAppointment(@RequestBody Map<String, Object> requestData) {

        int patientId;
        Object patientIdObj = requestData.get("patientId");
        if (patientIdObj instanceof String) {
            patientId = Integer.parseInt((String) patientIdObj);
        } else if (patientIdObj instanceof Integer) {
            patientId = (Integer) patientIdObj;
        } else {
            throw new IllegalArgumentException("patientId에 대한 잘못된 유형");
        }

        int guardian;
        Object guardianObj = requestData.get("guardian");
        if (guardianObj instanceof Integer) {
            guardian = (Integer) guardianObj;
        } else if (guardianObj instanceof String) {
            guardian = Integer.parseInt((String) guardianObj);
        } else {
            throw new IllegalArgumentException("guardian에 대한 잘못된 유형");
        }

        int timeSlotNo;
        Object timeSlotObj = requestData.get("timeSlotNo");
        if (timeSlotObj instanceof Integer) {
            timeSlotNo = (Integer) timeSlotObj;
        } else if (timeSlotObj instanceof String) {
            timeSlotNo = Integer.parseInt((String) timeSlotObj);
        } else {
            throw new IllegalArgumentException("timeSlotNo에 대한 잘못된 유형");
        }

        String symptom = requestData.get("symptom") instanceof String ? (String) requestData.get("symptom") : null;
        String note = requestData.get("note") instanceof String ? (String) requestData.get("note") : null;

        // For appoStatus
        int appoStatus;
        Object appoStatusObj = requestData.get("appoStatus");
        if (appoStatusObj instanceof Integer) {
            appoStatus = (Integer) appoStatusObj;
        } else if (appoStatusObj instanceof String) {
            appoStatus = Integer.parseInt((String) appoStatusObj);
        } else {
            throw new IllegalArgumentException("appoStatus에 대한 잘못된 유형");
        }

        HashMap<String, Boolean> response = new HashMap<>();
        try {
            AppoRequestDto appoDto = new AppoRequestDto();
            appoDto.setPatientId(patientId);
            appoDto.setGuardian(guardian);
            appoDto.setTimeSlotNo(timeSlotNo);
            appoDto.setSymptom(symptom);
            appoDto.setNote(note);
            appoDto.setAppoStatus(appoStatus);

            Appointment appointment = new Appointment(appoDto);
            appoRepository.save(appointment);

            response.put("success", true);
        } catch(Exception e) {
            response.put("success", false);
            e.printStackTrace();
        }
        return response;
    }



}
