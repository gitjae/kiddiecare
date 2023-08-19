package com.spring.kiddiecare.domain.hospital;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppoResponseRepository extends JpaRepository<AppoResponseDto,Integer> {

//    public AppoResponseDto findAllBySlotNo(int slotNo);

    @Query(nativeQuery = true, value = "SELECT * FROM appoView WHERE slot_no=?1")
    public List<AppoResponseDto> findAllBySlotNo(int slotNo);

    @Query(nativeQuery = true, value = "SELECT * FROM appoView WHERE slot_no=?1")
    public List<AppoResponseDto> findAllBySlotNoAndPage(int slotNo, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM appoView WHERE slot_no=?1")
    public List<AppoResponseDto> findAllByTimeSlotNo(int slotNo);



}
