package com.spring.kiddiecare.domain.hospital;

import org.springframework.data.jpa.repository.JpaRepository;

// 병원코드 찾기
public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    Hospital findByYkiho(String ykiho);
}

