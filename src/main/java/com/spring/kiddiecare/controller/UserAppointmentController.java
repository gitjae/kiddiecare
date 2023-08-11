package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.children.Children;
import com.spring.kiddiecare.domain.children.ChildrenRepository;
import com.spring.kiddiecare.domain.doctor.Doctor;
import com.spring.kiddiecare.domain.hospital.Hospital;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimit;
import com.spring.kiddiecare.domain.user.User;
import com.spring.kiddiecare.domain.user.UserRepository;
import com.spring.kiddiecare.service.DoctorService;
import com.spring.kiddiecare.service.HospitalService;
import com.spring.kiddiecare.service.TimeSlotsLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SessionAttributes({"log"})
@Controller
@RequestMapping("/appointment")
public class UserAppointmentController {
    private HospitalService hospitalService;
    private DoctorService doctorService;
    private UserRepository userRepository;
    private ChildrenRepository childrenRepository;
    private TimeSlotsLimitService timeSlotsLimitService;
}
