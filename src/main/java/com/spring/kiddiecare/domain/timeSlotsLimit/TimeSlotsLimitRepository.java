package com.spring.kiddiecare.domain.timeSlotsLimit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Repository
public interface TimeSlotsLimitRepository extends JpaRepository<TimeSlotsLimit, Integer> {
    List<TimeSlotsLimit> findTimeSlotsLimitByYkihoAndDate(String ykiho, Date date);

    List<TimeSlotsLimit> findTimeSlotsLimitsByYkihoAndDateAndTime(String ykiho, Date date, Time time);

    List<TimeSlotsLimit> findTimeSlotsLimitByYkihoAndDateAndDoctorNo(String ykiho, Date date, Long doctorNo);

    // enable이 0보다 큰 TimeSlotsLimit 정보들
    List<TimeSlotsLimit> findByYkihoAndDateAndDoctorNoAndEnableGreaterThanEqual(String ykiho, Date date, Long doctorNo, int enable);

    // hospitalAppoint no, time_slots_limit를 통해 해당 값 받아옴

    //예약 생성 전에 예약된 날짜는 다 빼줄려고 데이터 보내줌
    List<TimeSlotsLimit> findDateByYkihoAndDoctorNo(String ykiho, long doctorNo);

    // 예약 생성 시 ykiho, doctorNo, date, time이 중복될 시 거르는 용도
//    List<TimeSlotsLimit> findNoByTimeSlotsLimit(TimeSlotsLimit timeSlotsLimit);
}
