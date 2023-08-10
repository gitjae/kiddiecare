package com.spring.kiddiecare.domain.timeSlotsLimit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeSlotsLimitRepository extends JpaRepository<TimeSlotsLimit, Long> {

}
