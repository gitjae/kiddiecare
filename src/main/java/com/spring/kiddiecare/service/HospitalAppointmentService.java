package com.spring.kiddiecare.service;

import com.spring.kiddiecare.domain.hospital.AppoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import static com.spring.kiddiecare.util.RandomUtil.createRanNum;

@RequiredArgsConstructor
@Service
public class HospitalAppointmentService {

    private final AppoRepository appoRepository;

    // 코드 중복체크
    public int duplCode() {
        int appo = 0;

        while(true) {
            int randNumber = Integer.parseInt(createRanNum());
            appo = appoRepository.findByNo(randNumber).getNo();

            if(appo == randNumber)
                break;

        }

        return appo;
    }

}
