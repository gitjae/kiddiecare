package com.spring.kiddiecare.service;

import com.spring.kiddiecare.domain.doctor.Doctor;
import com.spring.kiddiecare.domain.doctor.DoctorRepository;
import com.spring.kiddiecare.domain.doctor.DoctorResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    // 병원코드에 따라 의사 목록 검색
    public List<Doctor> findDoctorsByYkiho(String ykiho) {
        return doctorRepository.findByYkiho(ykiho);
    }

    @Transactional
    public void createDoctor(Doctor doctor){
        doctorRepository.save(doctor);
    }

    @Transactional
    public void deleteDoctor(long no){
        doctorRepository.deleteById(no);// 아이디 값은 pk이다!!
    }

    @Transactional
    public Doctor updateDoctor(DoctorResponseDto boardDto){
        Doctor doctor = doctorRepository.findById(boardDto.getNo()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 의사입니다.")
        );
        doctor.Update(boardDto);
        return doctor;
    }
}
