package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.hospitalAdmin.Admin;
import com.spring.kiddiecare.domain.hospitalAdmin.AdminRepository;
import com.spring.kiddiecare.domain.hospitalAdmin.AdminRequestDto;
import com.spring.kiddiecare.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.json.JSONObject;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("admin/info/")
public class AdminController {
    private final AdminService adminService;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageUploadController imageUploadController;

    @PostMapping("id/check")
    public Map adminIdDuplCheck(@ModelAttribute AdminRequestDto adminDto){
        JSONObject result = new JSONObject();
        System.out.println("adminDto 확인 "+adminDto);
        Admin admin = adminRepository.findByAdminId(adminDto.getAdminId());
        System.out.println("admin 확인"+admin);
        String message = admin == null ? "Not a duplicate value." : "duplicate value";
        result.put("response", message);
        return result.toMap();
    }

    @PostMapping("email/check")
    public Map sendAuthToken(@ModelAttribute AdminRequestDto adminDto){
        JSONObject result = new JSONObject();
        return result.toMap();
    }


    @PostMapping(value ="join", consumes = {"multipart/form-data"})
    public Map adminJoin(@ModelAttribute AdminRequestDto adminDto){
        JSONObject result = new JSONObject();

        // 데이터값 확인
        if(adminDto == null){
            return result.put("response","fail cause joinForm data is null").toMap();
        }

        // 데이터베이스에 있는지 확인
        Optional<Admin> userIsNull = Optional.ofNullable(adminRepository.findByAdminId(adminDto.getAdminId()));
        if(userIsNull.isEmpty()){

            // DB에 넣을 데이터 생성
            Admin admin = new Admin(adminDto);

            // 사진 파일 올리기
            String saveFile = imageUploadController.uploadFile(adminDto.getFile(),adminDto.getAdminId());
            if(saveFile == null){
                return result.put("response","fail").toMap();
            }
            admin.setFile(saveFile);

            // 비밀번호 encoded
            String encodedPassword = passwordEncoder.encode(adminDto.getAdminPw());
            admin.setAdminPw(encodedPassword);

            // DB에 저장
            try {
                adminService.joinAdminUserByAdmin(admin);
                result.put("response","success");
            }catch (Exception e){
                System.out.println(e);
                result.put("response","fail cause db error");
            }
        }else{
            result.put("response","fail cause user that already exists");
        }
        return result.toMap();
    }

    // pw and
    @PostMapping("pw/check")
    public Map adminPwSameCheck(@RequestBody AdminRequestDto adminDto, WebRequest request){
        JSONObject result = new JSONObject();
        Optional<String> session = Optional.ofNullable(request.getAttribute("log", WebRequest.SCOPE_SESSION).toString());
        if(session.isPresent()){
            Admin admin = adminRepository.findByAdminName(session.get());
            String message = admin.getAdminPw().equals(adminDto.getAdminPw()) ? "duplicate value" : "Not a duplicate value.";
            result.put("response",message);
        }else{
            result.put("response","fail session is null");
        }
//        Optional<Admin> admin = Optional.ofNullable(Optional.ofNullable(adminRepository.findByAdminId(adminDto.getAdminId()))
//                .orElseGet(() -> {
//                    result.put("response", "Not a duplicate value.");
//                    return null;
//                }));
//        if(admin.isPresent()) result.put("response","duplicate value");
        return result.toMap();
    }

    // TODO 전체적인 로직 수정 해야함
    @PutMapping("update/{updateItem}")
    public Map adminUpdate(@PathVariable String updateItem, @ModelAttribute AdminRequestDto adminDto, WebRequest request) {
        String sessionId = (String) request.getAttribute("log", WebRequest.SCOPE_SESSION);
        JSONObject result = new JSONObject();

        System.out.println(updateItem);

        // session 값 확인
        if (sessionId == null) {
            return result.put("response", "fail cause session does not exist.").toMap();
        }
        try{
            //어드민 이름은 그냥 수정가능하고, 어드민Email은 js에서 이메일 인증을 한번더 받으므로 그냥 update해주면 된다.
            if (updateItem.equals("adminName") || updateItem.equals("adminEmail")) {
                adminService.updateAdminByAdminDto(adminDto, sessionId);

                // 비밀번호의 경우 DB에서 한번 확인작업이 필요하다.
            } else if (updateItem.equals("adminPw")){
                String adminPw = adminDto.getAdminPw();
                String adminUpdatePw = adminDto.getUpdateAdminPw();

                Admin admin = adminRepository.findByAdminId(sessionId);

                if(passwordEncoder.matches(adminPw,admin.getAdminPw())){
                    String adminUpdatePwEncrypt = passwordEncoder.encode(adminUpdatePw);
                    adminDto.setAdminPw(adminUpdatePwEncrypt);
                    adminService.updateAdminByAdminDto(adminDto,sessionId);
                }else{
                    return result.put("response","fail cause pw is not matches").toMap();
                }
            }else{
                return result.put("response","fail cause updateItem not value").toMap();
            }
        }catch (Exception e){
            return result.put("response","fail cause DB error").toMap();
        }
        return result.put("response","success").toMap();
    }

    @DeleteMapping("leave")
    public Map delete(@ModelAttribute AdminRequestDto adminDto, WebRequest request){
        JSONObject result = new JSONObject();
        Optional<String> sessionId = Optional.ofNullable((String) request.getAttribute("log", WebRequest.SCOPE_SESSION));

        if(sessionId.isEmpty()){
            return result.put("response", "fail cause session does not exist.").toMap();
        }

        Optional<Admin> adminInfo = Optional.ofNullable(adminRepository.findByAdminId(sessionId.get()));
        if(adminInfo.isPresent()){
            try{
                String encryptPw = passwordEncoder.encode(adminDto.getAdminPw());
                if(passwordEncoder.matches(encryptPw,adminInfo.get().getAdminPw())){
                    adminService.deleteAdminByNo(adminInfo.get().getNo());
                }
            }catch (Exception e){
                return result.put("leave","fail cause cannot delete.").toMap();
            }
        }
        return result.put("response","success").toMap();
    }

    // TODO 의사 정보 받아서 저장하는 로직
    // TODO ADMIN MAIN이 없음


}
