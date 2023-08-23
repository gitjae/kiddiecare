package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.doctor.Doctor;
import com.spring.kiddiecare.domain.doctor.DoctorRepository;
import com.spring.kiddiecare.domain.doctor.DoctorResponseDto;
import com.spring.kiddiecare.domain.excel.ExcelData;
import com.spring.kiddiecare.domain.excel.ExcelDataRepository;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimit;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimitRepository;
import com.spring.kiddiecare.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestMapping("api/v1/doctor")
@RequiredArgsConstructor
@RestController
public class DoctorController {
    private final TimeSlotsLimitRepository timeSlotsLimitRepository;
    private final DoctorRepository doctorRepository;
    private final DoctorService doctorService;
    private final ExcelDataRepository excelDataRepository;

    @GetMapping("schedule")
    public Map onSchedule(@ModelAttribute TimeSlotsLimit timeSlotsLimit){
        String ykiho = timeSlotsLimit.getYkiho();
        Date date = timeSlotsLimit.getDate();
        Time time = timeSlotsLimit.getTime();

        List<TimeSlotsLimit> timeSlotsLimits = timeSlotsLimitRepository.findTimeSlotsLimitsByYkihoAndDateAndTime(ykiho, date, time);
        ArrayList<DoctorResponseDto> doctors = new ArrayList<>();

        for(TimeSlotsLimit slot : timeSlotsLimits){
            Long no = slot.getDoctorNo();
            Optional<Doctor> foundDoctor = doctorRepository.findById(no);
            if(foundDoctor.isPresent()){
                Doctor doctor = foundDoctor.get();
                DoctorResponseDto doctorDto = new DoctorResponseDto();
                doctorDto.setNo(doctor.getNo());
                doctorDto.setDoctorName(doctor.getDoctorName());
                doctors.add(doctorDto);
            }
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("doctors", doctors);
        jsonObject.put("slots", timeSlotsLimits);

        return jsonObject.toMap();
    }

    @GetMapping("select")
    public Map searchDoctorData(WebRequest request){
        JSONObject jsonObject = new JSONObject();
        String ykiho = (String) request.getAttribute("Ykiho",WebRequest.SCOPE_SESSION);

        if(ykiho == null){
            return jsonObject.put("response","no ykiho").toMap();
        }

        List<Doctor> doctorList = doctorRepository.findAllByYkiho(ykiho);
        if(doctorList == null){
            return jsonObject.put("response","no data").toMap();
        }
        jsonObject.put("response","success");
        jsonObject.put("data",doctorList);
        return jsonObject.toMap();
    }

    @PostMapping(value ="create", consumes = {"multipart/form-data"})
    public Map addDoctorData(@ModelAttribute DoctorResponseDto doctorDto,WebRequest request){
        JSONObject jsonObject = new JSONObject();

        // 세션에서 Ykiho 확인
        String ykiho = (String) request.getAttribute("Ykiho",WebRequest.SCOPE_SESSION);
        if(ykiho == null){
            return jsonObject.put("response","fail no ykiho").toMap();
        }

        if (doctorDto != null){
            Doctor doctorData  = doctorRepository.findByYkihoAndDoctorName(ykiho,doctorDto.getDoctorName());
            if (doctorData != null){
                return jsonObject.put("response","fail cause already in DB").toMap();
            }

            doctorDto.setYkiho(ykiho);
            Doctor doctor = new Doctor(doctorDto);

            try{
                doctorService.createDoctor(doctor);
                return jsonObject.put("response","success").toMap();
            }catch (Exception e){
                return jsonObject.put("response","fail cause DB error").toMap();
            }
        }

        return jsonObject.put("response","fail").toMap();
    }

    @DeleteMapping("delete")
    public Map deleteDoctorData(@ModelAttribute DoctorResponseDto doctorDto,WebRequest request){
        JSONObject jsonObject = new JSONObject();

        System.out.println(doctorDto);

        String ykiho = (String) request.getAttribute("Ykiho",WebRequest.SCOPE_SESSION);
        if(ykiho == null){
            return jsonObject.put("response","fail no ykiho").toMap();
        }

        Doctor doctorData  = doctorRepository.findByYkihoAndDoctorName(ykiho,doctorDto.getDoctorName());
        if(doctorData != null){
            try {
                doctorService.deleteDoctor(doctorData.getNo());
                return jsonObject.put("response","success").toMap();
            }catch (Exception e){
                return jsonObject.put("response","fail cause DB error").toMap();
            }
        }
        return jsonObject.put("response","fail").toMap();
    }

    @PutMapping(value ="update", consumes = {"multipart/form-data"})
    public Map updateDoctorData(@ModelAttribute DoctorResponseDto doctorDto, WebRequest request){
        JSONObject jsonObject = new JSONObject();

        String ykiho = (String) request.getAttribute("Ykiho",WebRequest.SCOPE_SESSION);
        if(ykiho == null){
            return jsonObject.put("response","fail no ykiho").toMap();
        }

        Doctor doctorData  = doctorRepository.findByYkihoAndDoctorName(ykiho,doctorDto.getDoctorName());
        if(doctorData != null){
            try {
                Doctor doctorUpdate = doctorService.updateDoctor(doctorDto);
                jsonObject.put("response","success");
                jsonObject.put("data",doctorDto);
                return jsonObject.toMap();
            }catch (Exception e){
                return jsonObject.put("response","fail cause DB error").toMap();
            }
        }
        return jsonObject.put("response","fail").toMap();
    }

    @GetMapping("subject")
    public Map getDoctorSubject(){
        JSONObject jsonObject = new JSONObject();
        ArrayList<String> data = new ArrayList<>();
        List<ExcelData> subjectList  = excelDataRepository.findAll();
        for(ExcelData items : subjectList){
            data.add(items.getSubject());
        }
        jsonObject.put("response","success");
        jsonObject.put("data",data);
        return jsonObject.toMap();
    }

}
