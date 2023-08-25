package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.children.Children;
import com.spring.kiddiecare.domain.children.ChildrenRepository;
import com.spring.kiddiecare.domain.doctor.Doctor;
import com.spring.kiddiecare.domain.doctor.DoctorRepository;
import com.spring.kiddiecare.domain.hospital.*;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimit;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimitRepository;
import com.spring.kiddiecare.domain.user.User;
import com.spring.kiddiecare.domain.user.UserRepository;
import com.spring.kiddiecare.service.DoctorService;
import com.spring.kiddiecare.service.HospitalService;
import com.spring.kiddiecare.service.TimeSlotsLimitService;
import com.spring.kiddiecare.service.UserAppointmentService;
import com.spring.kiddiecare.util.AppoView;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@SessionAttributes({"log"})
@RestController
@RequestMapping("api/v1/appo")
public class UserAppointmentController {
    private final UserRepository userRepository;
    private final AppoRepository appoRepository;
    private final TimeSlotsLimitRepository timeSlotsLimitRepository;
    private final HospitalRepository hospitalRepository;
    private final DoctorRepository doctorRepository;
    private final ChildrenRepository childrenRepository;
    private final UserAppointmentService userAppointmentService;

    @GetMapping("list/{page}")
    public Map appoPage(WebRequest request, @PageableDefault(size = 2) Pageable pageable, @PathVariable int page){
        String log = (String) request.getAttribute("log", WebRequest.SCOPE_SESSION);

        JSONObject jsonObject = new JSONObject();

        Optional<User> foundUser = userRepository.findUserById(log);
        if (foundUser.isPresent()){
            User user = foundUser.get();
            List<Appointment> appointments = appoRepository.findAllByGuardian(pageable.withPage(page-1), user.getNo());

            List<AppoView> appoDtos = new ArrayList<>();

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

            jsonObject.put("appointments", appoDtos);
        }

        return jsonObject.toMap();
    }

    @PutMapping("update")
    public Map appoUpdate(@RequestBody AppoRequestDto appoDto, WebRequest request){
        JSONObject jsonObject = userAppointmentService.updateAppo(request, appoDto);

        return jsonObject.toMap();
    }

    @PutMapping("cancel")
    public Map appoCancel(@RequestParam String appoNo, WebRequest request){
        JSONObject jsonObject = userAppointmentService.appoCancel(appoNo, request);

        return jsonObject.toMap();
    }

    @DeleteMapping("delete")
    public Map appoDelete(@RequestParam String appoNo, WebRequest request){
        JSONObject jsonObject = userAppointmentService.appoDelete(appoNo, request);

        return jsonObject.toMap();
    }
}
