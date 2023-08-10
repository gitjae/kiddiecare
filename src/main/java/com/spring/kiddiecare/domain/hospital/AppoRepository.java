package com.spring.kiddiecare.domain.hospital;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppoRepository extends JpaRepository<Appointment, Integer> {
    public Appointment findByNo(Integer no);
}
