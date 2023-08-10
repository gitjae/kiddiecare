package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.hospitalAdmin.Admin;
import com.spring.kiddiecare.domain.hospitalAdmin.AdminRepository;
import com.spring.kiddiecare.domain.hospitalAdmin.AdminRequestDto;
import com.spring.kiddiecare.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
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

//    @GetMapping("")
//    @PutMapping("update/{adminName}")
//    public Map adminUpdate(@PathVariable String adminName, @RequestBody AdminRequestDto adminDto, WebRequest request){
////        Optional<String> session = Optional.ofNullable(request.getAttribute("log", WebRequest.SCOPE_SESSION));
//        JSONObject result = new JSONObject();
//
//        if(session.isEmpty()){
//            result.put("join","fail");
//            return result.toMap();
//        }
//
//        adminRepository.findByAdminName()
//
//        try{
//            adminRepository.findById(adminDto.get).orElseThrow(
//                    ()-> new IllegalArgumentException("존재하지 않는 게시글 입니다.")
//            );(userDto.getUsername());
//            adminService.createUser(userDto);
//            result.put("join","success");
//        }catch (Exception e){
//            e.printStackTrace();
//            result.put("join","fail");
//        }
//        return result.toMap();
//    }
//
//    @DeleteMapping("leave/{adminName}")
//    public Map delete(@PathVariable String username){
//        JSONObject result = new JSONObject();
//        try{
//            adminService.getUserById(username);
//            adminService.deleteUserById(username);
//            result.put("leave","success");
//        }catch (Exception e){
//            e.printStackTrace();
//            result.put("leave","fail");
//        }
//        return result.toMap();
//    }

}
