package com.spring.kiddiecare.service;

import com.spring.kiddiecare.domain.Hospital.Hospital;
import com.spring.kiddiecare.domain.Hospital.HospitalRepository;
import com.spring.kiddiecare.domain.UserAppointment.UserAppointment;
import com.spring.kiddiecare.domain.UserAppointment.UserAppointmentDto;
import com.spring.kiddiecare.domain.UserAppointment.UserAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAppointment_Service {

    @Autowired
    private UserAppointmentRepository appointmentRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    // 예약 중복 검사
    // 병원 예약 가능 시간
    // 사용자 정보 확인

    @Transactional
    public String book(UserAppointmentDto requestDto) {
        Hospital hospital = hospitalRepository.findById(requestDto.getYkiho())
                .orElseThrow(() -> new IllegalArgumentException("병원 정보를 찾을 수 없습니다."));

        if (hospital.getReservesSlots() >= hospital.getHospitalMaxSlots()) {
            return "예약 가능한 좌석이 없습니다.";
        }

        UserAppointment appointment = new UserAppointment();
        appointment.setYkiho(requestDto.getYkiho());
        appointment.setUserId(requestDto.getUserId());
        appointment.setChildId(requestDto.getChildId());

        appointmentRepository.save(appointment);

        hospital.setReservesSlots(hospital.getReservesSlots() + 1);
        hospitalRepository.save(hospital);

        return "예약이 완료되었습니다.";
    }
}