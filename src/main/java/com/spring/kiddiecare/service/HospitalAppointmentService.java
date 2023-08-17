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
        appo.setAppoStatus(status);

        appoRepository.save(appo);
    }

}
