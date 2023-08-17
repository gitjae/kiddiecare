package com.spring.kiddiecare.domain.hospital;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppoResponseObjectRepository extends JpaRepository<Appointment, String> {
    public Optional<Appointment> findByNo(String no);
}
