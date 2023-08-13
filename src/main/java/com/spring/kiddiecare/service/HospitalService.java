package com.spring.kiddiecare.service;

import com.spring.kiddiecare.domain.hospital.Hospital;
import com.spring.kiddiecare.domain.hospital.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
