package com.spring.kiddiecare.domain.hospital;

import org.springframework.data.jpa.repository.JpaRepository;

// 병원코드 찾기
public interface HospitalRepository extends JpaRepository<Hospital, String> {
    // 병원코드 찾기
    Hospital findByYkiho(String ykiho);

    // 병원 이름으로 병원 찾기
    Hospital findByHospitalName(String hospitalName);

    // 병원 코드로 병원 이름 찾기
    Hospital findYkihoByHospitalName(String hospitalName);
}

