package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.hospitalAdmin.Admin;
import com.spring.kiddiecare.domain.hospitalAdmin.AdminRequestDto;
import com.spring.kiddiecare.domain.hospitalAdmin.AdminRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
@SessionAttributes({"log"}) // 세션을 사용하고 있다라는걸 바인딩 해줘야함
@RequestMapping("admin")
public class AdminLogController {

    private final AdminRespository adminRespository;

//    @SessionScope
//    @PostMapping(value = "login/check")
//    public ModelAndView login(@ModelAttribute AdminRequestDto adminDto, WebRequest request) {
//        boolean sessionIsNull = Optional.ofNullable(request.getAttribute("log", WebRequest.SCOPE_SESSION)).isEmpty();
//        Optional<String> adminId = Optional.ofNullable(adminDto.getAdminId());
//        Optional<String> adminPw = Optional.ofNullable(adminDto.getAdminPw());
//        String prevUrl = request.getHeader("referer");
//        String redirectUrl = "redirect:" + prevUrl;
//
//        if (sessionIsNull || adminId.isEmpty() || adminPw.isEmpty()){
//            return new ModelAndView(redirectUrl);
//        }
//
//        Admin admin = adminRespository.findByAdminIdAndAdminPw(adminId.get(), adminPw.get());
//        if (admin != null) {
//            ModelAndView modelAndView = new ModelAndView(redirectUrl);
//            modelAndView.addObject("log", admin.getAdminName());
//            return modelAndView;
//        } else {
//            return new ModelAndView(redirectUrl);
//        }
//    }

    @SessionScope
    @PostMapping(value = "login/check")
    public ModelAndView login(@ModelAttribute AdminRequestDto adminDto, WebRequest request) {
        String prevUrl = request.getHeader("referer");
        String redirectUrl = "redirect:" + prevUrl;
        System.out.println(adminDto);

        return Optional.ofNullable(request.getAttribute("log", WebRequest.SCOPE_SESSION))
                .filter(__ -> adminDto.getAdminId() != null && adminDto.getAdminPw() != null)
                .map(__ -> adminRespository.findByAdminIdAndAdminPw(adminDto.getAdminId(), adminDto.getAdminPw()))
                .map(admin -> {
                    ModelAndView modelAndView = new ModelAndView("/");
                    modelAndView.addObject("log", admin.getAdminName());
                    return modelAndView;
                })
                .orElse(new ModelAndView(redirectUrl));
    }


    @GetMapping("logout")
    public String logout(WebRequest request, SessionStatus status){ // 세션에 대한 권한을 준다. 없으면 remove가 실행되지 않는다.
        status.setComplete();
        request.removeAttribute("log",WebRequest.SCOPE_SESSION);// scope는 지정 되어있음
        return "redirect:/"; //약속된 키워드임 ㅇㅇ
    }
}
