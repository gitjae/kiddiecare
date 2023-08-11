package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.children.Children;
import com.spring.kiddiecare.domain.children.ChildrenRepository;
import com.spring.kiddiecare.domain.children.ChildrenResponseDto;
import com.spring.kiddiecare.domain.doctor.Doctor;
import com.spring.kiddiecare.domain.doctor.DoctorRepository;
import com.spring.kiddiecare.domain.hospital.*;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimit;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimitRepository;
import com.spring.kiddiecare.domain.user.User;
import com.spring.kiddiecare.domain.user.UserRepository;
import com.spring.kiddiecare.domain.user.UserResponseDto;
import com.spring.kiddiecare.util.AppoView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final UserRepository userRepository;
    private final ChildrenRepository childrenRepository;
    private final AppoRepository appoRepository;
    private final HospitalRepository hospitalRepository;
    private final TimeSlotsLimitRepository timeSlotsLimitRepository;
    private final DoctorRepository doctorRepository;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("join")
    public String join(){
        return "join";
    }

    @GetMapping("login")
    public String userLogin(){
        return "login";
    }
    @GetMapping("/admin/login")
    public String adminLogin(){return "adminLogin";}

    @GetMapping("admin/appointment")
    public String hospitalReservationForm() {
        return "hospitalAppointmentForm";
    }

    @GetMapping("/geolocation")
    public String geolocation() { return "geolocation"; }

    @GetMapping("mypage/{id}")
    public String mypage(@PathVariable String id
            , @PageableDefault(size = 2, direction = Sort.Direction.DESC) Pageable pageable
            , Model model) {
        Optional<User> foundUser = userRepository.findUserById(id);

        if(foundUser.isPresent()){
            User user = foundUser.get();
            List<Children> children = childrenRepository.findByParentNo(pageable, user.getNo());
            List<Appointment> appointments = appoRepository.findAllByGuardian(pageable, user.getNo());

            UserResponseDto userDto = new UserResponseDto(user);
            List<ChildrenResponseDto> childrendDtos = new ArrayList<>();
            List<AppoView> appoDtos = new ArrayList<>();
            for(Children child : children){
                ChildrenResponseDto childDto = new ChildrenResponseDto(child);
                childrendDtos.add(childDto);
            }
            for(Appointment appo : appointments){
                // time
                Optional<TimeSlotsLimit> timeSlotsLimitOptional = timeSlotsLimitRepository.findById(appo.getTimeSlotNo());
                TimeSlotsLimit timeSlotsLimit = timeSlotsLimitOptional.get();
                // hosp
                Hospital hospital = hospitalRepository.findByYkiho(timeSlotsLimit.getYkiho());
                // doc
                Optional<Doctor> doctorOptional = doctorRepository.findById(new Long(timeSlotsLimit.getDoctorNo()));
                Doctor doctor = doctorOptional.get();
                // child
                Optional<Children> childrenOptional = childrenRepository.findById(appo.getPatientId());
                Children child = childrenOptional.get();


                AppoView appoView = new AppoView(appo, hospital, timeSlotsLimit, doctor, child);
                appoDtos.add(appoView);
            }

            model.addAttribute("user", userDto);
            model.addAttribute("children", childrendDtos);
            model.addAttribute("appointments", appoDtos);
        }
        return "mypage";
    }
}
