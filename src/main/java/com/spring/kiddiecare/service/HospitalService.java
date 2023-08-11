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
        System.out.println("service: " +ykiho);
        return hospitalRepository.findByYkiho(ykiho);
    }

}
