package com.spring.kiddiecare.service;

import com.spring.kiddiecare.domain.hospital.Hospital;
import com.spring.kiddiecare.domain.hospital.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    public List<Hospital> findAllHospitals() {
        return hospitalRepository.findAll();
    }

    public Hospital findHospitalByYkiho(String ykiho) {
        return hospitalRepository.findByYkiho(ykiho);
    }

    // 코드로 병원 이름 찾기
    public String findHospitalNameByYkiho(String ykiho) {
        String hospitalName = hospitalRepository.findNameByYkiho(ykiho);
        System.out.println("Hospital Name: " + hospitalName);
        return hospitalName;
    }

    // 병원 이름으로 병원 찾기
    public Hospital findHospitalByHospitalName(String hospitalName) {
        return hospitalRepository.findByHospitalName(hospitalName);
    }

//    public Hospital findHospitalFromAPI(String hospitalName) {
//        final String apiUrl = "https://api.example.com/hospitals?name=" + hospitalName;
//
//        // API 호출
//        Hospital hospital = restTemplate.getForObject(apiUrl, Hospital.class);
//
//        if(hospital == null) {
//            throw new RuntimeException("API에서 병원 정보를 찾을 수 없습니다.");
//        }
//
//        return hospital;
//    }



}
