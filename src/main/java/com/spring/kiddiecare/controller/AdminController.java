package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.hospitalAdmin.Admin;
import com.spring.kiddiecare.domain.hospitalAdmin.AdminRepository;
import com.spring.kiddiecare.domain.hospitalAdmin.AdminRequestDto;
import com.spring.kiddiecare.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("admin/info/")
public class AdminController {
    private final AdminService adminService;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("id/check")
    public Map adminIdDuplCheck(@RequestBody AdminRequestDto adminDto){
        JSONObject result = new JSONObject();
        Admin admin = adminRepository.findByAdminId(adminDto.getAdminId());
        String message = admin == null ? "Not a duplicate value." : "duplicate value";
        result.put("response", message);
        return result.toMap();
    }

    @PostMapping("join")
    public Map adminJoin(@RequestBody AdminRequestDto adminDto){
        JSONObject result = new JSONObject();
        // 데이터값 확인
        if(adminDto == null){
            result.put("response","fail cause joinForm data is null");
            return result.toMap();
        }

        // 데이터베이스에 있는지 확인
        Optional<Admin> userIsNull = Optional.ofNullable(adminRepository.findByAdminId(adminDto.getAdminId()));
        if(userIsNull.isEmpty()){
            // 비밀번호 encoded
            String encodedPassword = passwordEncoder.encode(adminDto.getAdminPw());
            adminDto.setAdminPw(encodedPassword);
            Admin admin = new Admin(adminDto);
            try {
                adminService.joinAdminUserByAdmin(admin);
            }catch (Exception e){
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
    @PutMapping("update/{adminName}")
    public Map adminUpdate(@PathVariable String adminName, @RequestBody AdminRequestDto adminDto, WebRequest request){
        Optional<String> session = Optional.ofNullable(request.getAttribute("log", WebRequest.SCOPE_SESSION).toString());
        JSONObject result = new JSONObject();

        //세션 확인
        if(session.isEmpty()){
            result.put("response","fail cause session does not exist.");
            return result.toMap();
        }

        // 세션값과 수정하려는 정보와 일치하는지 확인
        if(!session.get().equals(adminName)) {
            result.put("response", "fail cause session and admin do not match.");
            return result.toMap();
        }

        // 비밀번호 Encrypt
        if(adminDto == null){
            result.put("response", "fail cause joinForm data is null.");
            return result.toMap();
        }else{
            adminDto.setAdminPw(passwordEncoder.encode(adminDto.getAdminPw()));
        }

        //DB에서 데이터가 있는지 확인후 수정 로직
        try {
            adminService.updateAdminByNo(adminDto);
            result.put("response", "success");
        } catch (Exception e) {
            result.put("response", "fail cause cannot save.");
        }

        return result.toMap();
    }

    @DeleteMapping("leave/{adminName}")
    public Map delete(@PathVariable String adminName, WebRequest request,AdminRequestDto adminDto){
        JSONObject result = new JSONObject();
        Optional<String> session = Optional.ofNullable(request.getAttribute("log", WebRequest.SCOPE_SESSION).toString());

        if(session.isEmpty()){
            result.put("join","fail cause session does not exist.");
            return result.toMap();
        }

        Optional<Admin> adminInfo = Optional.ofNullable(adminRepository.findByadminNameAndAdminPw(adminName,adminDto.getAdminPw()));
        if(adminInfo.isPresent()){
            try{
                adminService.deleteAdminByNo(adminInfo.get().getNo());
                result.put("leave","success");
            }catch (Exception e){
                result.put("leave","fail cause cannot delete.");
            }
        }
        return result.toMap();
    }

}
