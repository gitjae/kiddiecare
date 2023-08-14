package com.spring.kiddiecare.service;

import com.spring.kiddiecare.domain.hospital.AppoRepository;
import com.spring.kiddiecare.domain.hospital.Appointment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import static com.spring.kiddiecare.util.RandomUtil.createRanNum;

// RandomUtil의 createRunNum() 메소드 사용
@RequiredArgsConstructor
@Service
public class HospitalAppointmentService {

    private final AppoRepository appoRepository;

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

        // ** 수정전
//        while(true) {
//            int randNumber = Integer.parseInt(createRanNum());
//            appo = appoRepository.findByNo(randNumber).getNo();
//
//            if(appo == randNumber)
//                break;
//
//        }

//        return appo;

    }

}
