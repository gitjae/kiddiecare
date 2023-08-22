package com.spring.kiddiecare.service;

import com.spring.kiddiecare.domain.hospital.*;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityNotFoundException;

import java.util.Optional;

import static com.spring.kiddiecare.util.RandomUtil.createRanNum;

// RandomUtil의 createRunNum() 메소드 사용
@RequiredArgsConstructor
@Service
public class HospitalAppointmentService {

    private final AppoRepository appoRepository;
    private final AppoResponseObjectRepository appoResponseObjectRepository;
    private final TimeSlotsLimitService timeSlotsLimitService;

    // 코드 중복체크
    public String duplCode() {
        // ** 수정한거
        String ranNum;
        Appointment foundAppointment;

        while(true) {
            ranNum = createRanNum();
            foundAppointment = appoRepository.findByNo(ranNum);

            if(foundAppointment == null) {
                break;
            }
        }
        return ranNum;
    }

    @Transactional
    public void changeTimeSlot(String hospAppoNo, int timeSlotNo) {
       Optional<Appointment> foundAppo = appoResponseObjectRepository.findByNo(hospAppoNo);

        // 존재하면 변경한 timeSlotNo 저장
        if(foundAppo.isPresent()){
            Appointment appo = foundAppo.get();
            appo.setTimeSlotNo(timeSlotNo);
            appoResponseObjectRepository.save(appo);
        }
        // 변경한 timeSlotNo 저장
//        appointmentResponseDto.setTimeSlotNo(timeSlotNo);
//        appoResponseObjectRepository.save(appointmentResponseDto);
    }

    @Transactional
    public void updateStatus(String appoNo, int status) {
        Appointment appo = appoRepository.findByNo(appoNo);

        // timeslot도 minus 필요
        if(appo != null) {
            // appoNo의 timeslotNo 찾기
            int timeNo = appo.getTimeSlotNo();

            // 1. 예약완료 -> 예약취소 (enable+1)
            if(status == 2) {
                // timeno 차감해서 저장
                timeSlotsLimitService.plusEnable(timeNo);

            }
            // 예약 상태 변경
            appo.setAppoStatus(status);
            // 변경한 appo 저장
            appoRepository.save(appo);
        }

    }

}
