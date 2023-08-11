package com.spring.kiddiecare.domain.timeSlotsLimit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TimeSlotsLimitRepository extends JpaRepository<TimeSlotsLimit, Integer> {
    List<TimeSlotsLimit> findByYkiho(String ykiho);
    List<TimeSlotsLimit> findByYkihoAndDate(String ykiho, Date date);
}
