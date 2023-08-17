package com.spring.kiddiecare.domain.timeSlotsLimit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TimeSlotsLimitRepository extends JpaRepository<TimeSlotsLimit, Integer> {
    List<TimeSlotsLimit> findTimeSlotsLimitByYkihoAndDate(String ykiho, Date date);

    List<TimeSlotsLimit> findTimeSlotsLimitByYkihoAndDateAndDoctorNo(String ykiho, Date date, Long doctorNo);

    // enable이 0보다 큰 TimeSlotsLimit 정보들
    List<TimeSlotsLimit> findByYkihoAndDateAndDoctorNoAndEnableGreaterThanEqual(String ykiho, Date date, Long doctorNo, int enable);

    // hospitalAppoint no, time_slots_limit를 통해 해당 값 받아옴
}
