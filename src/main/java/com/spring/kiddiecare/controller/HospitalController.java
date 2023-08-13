package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.doctor.Doctor;
import com.spring.kiddiecare.domain.hospital.Hospital;
import com.spring.kiddiecare.service.DoctorService;
import com.spring.kiddiecare.service.HospitalService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Controller
@RestController
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/HospitalHospitalInfo")
    public String hospitalInfo(Model model) {
        model.addAttribute("hospitals", hospitalService.findAllHospitals());
        return "HospitalHospitalInfo";
    }

    @GetMapping("api/appointment/hospitalDetail")
    public Map<String, Object> getHospitalDetails(@RequestParam("hospitalName") String hospitalName) {
        JSONObject jsonObject = new JSONObject();

        // API에서 데이터 가져오기 (예시로 주석 처리)
        // Hospital apiHospitalData = hospitalService.findHospitalFromAPI(hospitalName);
        // response.put("apiHospitalData", apiHospitalData);

        // 병원 이름으로 병원 데이터 가져오기
        Hospital dbHospitalData = hospitalService.findHospitalByHospitalName(hospitalName);
        jsonObject.put("dbHospitalData", dbHospitalData);

        if (dbHospitalData != null) {
            // 해당 병원의 ykiho로 의사 정보 가져오기
            List<Doctor> doctors = doctorService.findDoctorsByYkiho(dbHospitalData.getYkiho());
            jsonObject.put("doctors", doctors);
        }

        return jsonObject.toMap();
    }

    // 윤정이 작업 코드 주석처리 -> 희수
//    @GetMapping("/hospitalName/{ykiho}")
//    @ResponseBody
//    public Hospital hospitalName(@PathVariable String ykiho) {
//        return hospitalService.findHospitalByYkiho(ykiho);
//    }

}
