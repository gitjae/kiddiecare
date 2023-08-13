package com.spring.kiddiecare.service;

import com.spring.kiddiecare.domain.doctor.Doctor;
import com.spring.kiddiecare.domain.doctor.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    // 병원코드에 따라 의사 목록 검색
    public List<Doctor> findDoctorsByYkiho(String ykiho) {
        return doctorRepository.findByYkiho(ykiho);
    }
}
