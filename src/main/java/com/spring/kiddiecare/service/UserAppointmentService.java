package com.spring.kiddiecare.service;

import com.spring.kiddiecare.domain.hospital.HospitalRepository;
import com.spring.kiddiecare.domain.userAppointment.UserAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UserAppointmentService {

    @Autowired
    private UserAppointmentRepository appointmentRepository;

    @Autowired
    private HospitalRepository hospitalRepository;


}