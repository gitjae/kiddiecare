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
import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
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
    private final ImageUploadController imageUploadController;

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

    /**
     * 의사 정보를 받고 DB에 의사정보 저장
     * @param doctorDto 프론트에서 받아온 정보
     * @param request 세션 정보
     * @return 반환 json
     */
    @PostMapping(value ="create", consumes = {"multipart/form-data"})
    public Map addDoctorData(@ModelAttribute DoctorResponseDto doctorDto,WebRequest request){
        // 반환할 json
        JSONObject jsonObject = new JSONObject();

        System.out.println(doctorDto);

        // 세션에서 Ykiho 확인
        String ykiho = (String) request.getAttribute("Ykiho",WebRequest.SCOPE_SESSION);
        if(ykiho == null){
            return jsonObject.put("response","fail no ykiho").toMap();
        }

        // doctorDto 검사
        if (doctorDto != null){
            // doctor 중복 검사
            Doctor doctorDuplCheck  = doctorRepository.findByYkihoAndDoctorName(ykiho,doctorDto.getDoctorName());
            if (doctorDuplCheck != null){
                return jsonObject.put("response","fail cause already in DB").toMap();
            }

            // ykiho 저장
            doctorDto.setYkiho(ykiho);


            // s3 파일 올리기
            if(!doctorDto.getFile().isEmpty()){

                // 파일 확장자 확인
                String extension = FilenameUtils.getExtension(doctorDto.getFile().getOriginalFilename()); // 3
                if (!extension.equals("png") && !extension.equals("jpg")) {
                    return jsonObject.put("response","fail cause file extension not supported").toMap();
                }

                String saveFileUrl = imageUploadController.uploadFile(doctorDto.getFile(),doctorDto.getDoctorName());
                if(saveFileUrl == null){
                    return jsonObject.put("response","fail cause image upload error").toMap();
                }
                doctorDto.setDoctorImageUrl(saveFileUrl);
            }

            // Doctor vo에 넣고 저장
            Doctor doctor = new Doctor(doctorDto);

            try{
                doctorService.createDoctor(doctor);
                return jsonObject.put("response","success").toMap();
            }catch (Exception e){
                return jsonObject.put("response","fail cause DB error").toMap();
            }
        }
        return jsonObject.put("response","fail cause data is null").toMap();
    }

    @DeleteMapping("delete")
    public Map deleteDoctorData(@ModelAttribute DoctorResponseDto doctorDto, WebRequest request){
        JSONObject jsonObject = new JSONObject();

        // 세션에서 Ykiho 확인
        String ykiho = (String) request.getAttribute("Ykiho",WebRequest.SCOPE_SESSION);
        if(ykiho == null){
            return jsonObject.put("response","fail no ykiho").toMap();
        }

        Doctor doctorData  = doctorRepository.findByYkihoAndNo(ykiho,doctorDto.getNo());
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

        System.out.println(doctorDto);

        // 세션에서 Ykiho 확인
        String ykiho = (String) request.getAttribute("Ykiho",WebRequest.SCOPE_SESSION);
        if(ykiho == null){
            return jsonObject.put("response","fail no ykiho").toMap();
        }

        Doctor doctorData  = doctorRepository.findByYkihoAndNo(ykiho,doctorDto.getNo());
        if(doctorData != null){
            try {
                doctorService.updateDoctor(doctorDto);
                return jsonObject.put("response","success").toMap();
            }catch (Exception e){
                return jsonObject.put("response","fail cause DB error").toMap();
            }
        }
        return jsonObject.put("response","fail").toMap();
    }

    @GetMapping("subject")
    public Map getDoctorSubject(){
        JSONObject jsonObject = new JSONObject();
        List<ExcelData> subjectList  = excelDataRepository.findAll();

        JSONArray list = new JSONArray();

        for(ExcelData items : subjectList){
            JSONObject obj = new JSONObject();
            obj.put("no", items.getNo());
            obj.put("subject", items.getSubject());

            list.put(obj);
        }
        jsonObject.put("response","success");
        jsonObject.put("data", list);
        return jsonObject.toMap();
    }

}
