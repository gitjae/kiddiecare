package com.spring.kiddiecare.util.shift;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShiftRepository extends JpaRepository<Shift, Integer> {
    List<Shift> findAllByWeekDayAndActive(String weekDay, boolean active);

    Optional<Shift> findByDoctorNoAndWeekDay(long doctorNo, String weekDay);

    Optional<Shift> findByNo(int no);

    List<Shift> findAllByDoctorNo(long doctorNo);
}
