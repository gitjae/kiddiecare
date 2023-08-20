package com.spring.kiddiecare.service;

import com.spring.kiddiecare.domain.hospital.Hospital;
import com.spring.kiddiecare.domain.hospital.HospitalRepository;
import com.spring.kiddiecare.domain.likeHospital.LikeHospital;
import com.spring.kiddiecare.domain.likeHospital.LikeHospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikeHospitalService {
    @Autowired
    private LikeHospitalRepository likeHospitalRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    public List<Hospital> getLikedHospitalsByUserNo(int userNo) {
        List<LikeHospital> likedHospitals = likeHospitalRepository.findByUserNo(userNo);
        return likedHospitals.stream()
                .map(likeHospital -> hospitalRepository.findByYkiho(likeHospital.getYkiho()))
                .collect(Collectors.toList());
    }
}
