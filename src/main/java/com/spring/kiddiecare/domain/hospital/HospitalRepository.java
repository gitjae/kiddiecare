package com.spring.kiddiecare.domain.hospital;

import org.springframework.data.jpa.repository.JpaRepository;

// 병원코드 찾기
public interface HospitalRepository extends JpaRepository<Hospital, String> {
    // 병원코드 찾기
    Hospital findByYkiho(String ykiho);

//     병원코드로 병원 이름 찾기
    String findNameByYkiho(String ykiho);

}

