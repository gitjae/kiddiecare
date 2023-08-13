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
import com.spring.kiddiecare.service.ChildrenService;
import com.spring.kiddiecare.service.DoctorService;
import com.spring.kiddiecare.service.HospitalService;
import com.spring.kiddiecare.service.TimeSlotsLimitService;
import com.spring.kiddiecare.util.AppoView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SessionAttributes({"log"})
@RequiredArgsConstructor
@Controller
public class MainController {

    private final UserRepository userRepository;
    private final ChildrenRepository childrenRepository;
    private final AppoRepository appoRepository;
    private final HospitalRepository hospitalRepository;
    private final TimeSlotsLimitRepository timeSlotsLimitRepository;
    private final DoctorRepository doctorRepository;
    private final HospitalService hospitalService;
    private final DoctorService doctorService;
    private final ChildrenService childrenService;

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

    @GetMapping("/hospital/Search")
    public String hospitalSearch(){return "hospitalSearchList";}

    @GetMapping("admin/appointment")
    public String hospitalReservationForm() {
        return "hospitalAppointmentForm";
    }

    @GetMapping("admin/appointment/create")
    public String hospitalReservationCreate() {return  "hospitalAppointmentCreate";}

    @GetMapping("/geolocation")
    public String geolocation() { return "geolocation"; }

    @GetMapping("childRegister")
    public String childResister(){return "childRegister";}

    @GetMapping("/hospitalInfo")
    public String hospitalInfo(Model model) {
        model.addAttribute("hospitals", hospitalService.findAllHospitals());
        return "hospitalInfo";
    }

    @GetMapping("mypage")
    public String mypage(WebRequest request
            , @PageableDefault(size = 2, direction = Sort.Direction.DESC) Pageable pageable
            , Model model) {
        String log = (String) request.getAttribute("log", WebRequest.SCOPE_SESSION);

        if(log == null){
            return "login";
        }

        Optional<User> foundUser = userRepository.findUserById(log);

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
//            model.addAttribute("children", childrendDtos);
//            model.addAttribute("appointments", appoDtos);
        }
        return "mypage";
    }

    @GetMapping("appointment/booking")
    public String showBookingPage(
            @RequestParam("hospitalName") String hospitalName,
            @RequestParam("treatmentDate") String treatmentDate,
            @RequestParam("treatmentDay") String treatmentDay,
            @RequestParam("doctorNo") String doctorNo,
            @RequestParam("slotTime") String slotTime,
            @RequestParam("slotWeekday") String slotWeekday,
            @RequestParam("timeSlotNo") int timeSlotNo,
            @ModelAttribute("log") String userId,
            Model model) {

        // 병원 이름을 사용하여 ykiho 값을 조회
        String ykiho = hospitalService.findYkihoByHospitalName(hospitalName);

        // 병원 정보
        Hospital hospital = hospitalService.findHospitalByYkiho(ykiho);
        model.addAttribute("hospital", hospital);

        // 병원 이름
        String hospitalNameFromService = hospitalService.findHospitalNameByYkiho(ykiho); // 변수명 변경하여 중복을 피합니다.
        model.addAttribute("hospitalName", hospitalNameFromService);

        // 해당 병원의 의사 정보
        List<Doctor> doctors = doctorService.findDoctorsByYkiho(ykiho);
        model.addAttribute("doctors", doctors);

        // 진료 날짜와 요일
        model.addAttribute("treatmentDate", treatmentDate);
        model.addAttribute("treatmentDay", treatmentDay);

        // 슬롯 정보
        model.addAttribute("doctorNo", doctorNo);
        model.addAttribute("slotTime", slotTime);
        model.addAttribute("slotWeekday", slotWeekday);
        model.addAttribute("timeSlotNo", timeSlotNo);

        // 사용자 아이디로 사용자 이름 찾기
        User user = userRepository.findUserById(userId).orElse(null);
        if (user != null) {
            model.addAttribute("userName", user.getName());
            model.addAttribute("parentId", user.getNo());

            // 해당 사용자의 자녀 정보 가져오기
            List<Children> childrenList = childrenService.getChildrenByParentNo(user.getNo());
            model.addAttribute("childrenList", childrenList);
        }

        return "userBooking";
    }

    @GetMapping("appointment/hospitalDetail")
    public String showReservePage(@RequestParam("hospitalName") String hospitalName, Model model) {       // -> hospitalName 으로 변경하고 내용 삭제하고 ajax(api->HospitalInfoController의 getHospitalInfo, 우리DB)로 요청보낸 후 나머지는 js에서 처리
        return "hospitalDetail";
    }
}
