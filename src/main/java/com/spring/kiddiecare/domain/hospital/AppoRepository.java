package com.spring.kiddiecare.domain.hospital;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppoRepository extends JpaRepository<Appointment, Integer> {
//    public Appointment findByNo(Integer no);
    public Appointment findByNo(String no);


    public List<Appointment> findAllByGuardian(int userNo);

    public List<Appointment> findAllByGuardian(Pageable pageable, int userNo);

    public Optional<Appointment> findAppointmentByNo(String no);

}
