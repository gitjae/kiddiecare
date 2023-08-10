package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.doctor.Doctor;
import com.spring.kiddiecare.domain.hospital.Hospital;
import com.spring.kiddiecare.domain.user.User;
import com.spring.kiddiecare.domain.user.UserRepository;
import com.spring.kiddiecare.service.DoctorService;
import com.spring.kiddiecare.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SessionAttributes({"log"})
@Controller
@RequestMapping("/appointment")
public class UserAppointmentController {
    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private UserRepository userRepository;

    // 파라미터로 병원코드 받아서 단일 병원 정보 나타내기
    @GetMapping("/hospitalDetail")
    public String showReservePage(@RequestParam("ykiho") String ykiho, Model model) {
        // 병원 정보
        Hospital hospital = hospitalService.findHospitalByYkiho(ykiho);
        model.addAttribute("hospital", hospital);

        // 해당 병원의 의사 정보
        List<Doctor> doctors = doctorService.findDoctorsByYkiho(ykiho);
        model.addAttribute("doctors", doctors);

        return "hospitalDetail";
    }

    @GetMapping("/booking")
    public String showBookingPage(
            @RequestParam("ykiho") String ykiho,
            @RequestParam("treatmentDate") String treatmentDate,
            @RequestParam("treatmentDay") String treatmentDay,
            @ModelAttribute("log") String userId,
            Model model) {

        // 병원 정보
        Hospital hospital = hospitalService.findHospitalByYkiho(ykiho);
        model.addAttribute("hospital", hospital);

        // 해당 병원의 의사 정보
        List<Doctor> doctors = doctorService.findDoctorsByYkiho(ykiho);
        model.addAttribute("doctors", doctors);

        // 진료 날짜와 요일
        model.addAttribute("treatmentDate", treatmentDate);
        model.addAttribute("treatmentDay", treatmentDay);

        // 사용자 아이디로 사용자 이름 찾기
        User user = userRepository.findUserById(userId).orElse(null);
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }

        return "userBooking";
    }


}
