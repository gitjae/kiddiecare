package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.doctor.Doctor;
import com.spring.kiddiecare.domain.doctor.DoctorRepository;
import com.spring.kiddiecare.domain.doctor.DoctorResponseDto;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimit;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimitRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestMapping("api/v1/doctor")
@RequiredArgsConstructor
@RestController
public class DoctorController {
    private final TimeSlotsLimitRepository timeSlotsLimitRepository;
    private final DoctorRepository doctorRepository;

    @GetMapping("schedule")
    public Map onSchedule(@ModelAttribute TimeSlotsLimit timeSlotsLimit){
        String ykiho = timeSlotsLimit.getYkiho();
        Date date = timeSlotsLimit.getDate();
        Time time = timeSlotsLimit.getTime();

        List<TimeSlotsLimit> timeSlotsLimits = timeSlotsLimitRepository.findTimeSlotsLimitsByYkihoAndDateAndTime(ykiho, date, time);
        ArrayList<DoctorResponseDto> doctors = new ArrayList<>();

        for(TimeSlotsLimit slot : timeSlotsLimits){
            Long no = slot.getDoctorNo();
            Optional<Doctor> foundDoctor = doctorRepository.findById(no);
            if(foundDoctor.isPresent()){
                Doctor doctor = foundDoctor.get();
                DoctorResponseDto doctorDto = new DoctorResponseDto();
                doctorDto.setNo(doctor.getNo());
                doctorDto.setDoctorName(doctor.getDoctorName());
                doctors.add(doctorDto);
            }
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("doctors", doctors);
        jsonObject.put("slots", timeSlotsLimits);

        return jsonObject.toMap();
    }
}
