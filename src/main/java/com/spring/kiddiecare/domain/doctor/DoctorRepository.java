package com.spring.kiddiecare.domain.doctor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findByYkiho(String ykiho);
    List<Doctor> findAllByYkiho(String ykiho);
    Doctor findByYkihoAndDoctorName(String ykiho, String doctorName);
    Doctor findByYkihoAndNo(String ykiho, long no);
}
