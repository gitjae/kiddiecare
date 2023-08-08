package com.spring.kiddiecare.service;

import com.spring.kiddiecare.domain.Hospital.Hospital;
import com.spring.kiddiecare.domain.Hospital.HospitalRepository;
import com.spring.kiddiecare.domain.UserAppointment.UserAppointment;
import com.spring.kiddiecare.domain.UserAppointment.UserAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UserAppointmentService {

    @Autowired
    private UserAppointmentRepository appointmentRepository;

    @Autowired
    private HospitalRepository hospitalRepository;


}