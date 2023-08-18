package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.user.*;
//import com.spring.kiddiecare.domain.user.UserRepository;
//import com.spring.kiddiecare.domain.user.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@SessionAttributes({"log"})
@RequestMapping("api/v1")
@RestController
public class LogController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("login")
    public Map login(@RequestBody UserRequestDto userDto, Model model){
        Optional<User> user = userRepository.findUserByIdAndIsValid(userDto.getId(), true);
        JSONObject json = new JSONObject();
        if(!user.isEmpty() && passwordEncoder.matches(userDto.getPassword(), user.get().getPassword())){
        //if(!user.isEmpty() && user.get().getPassword().equals(userDto.getPassword())){
            model.addAttribute("log", userDto.getId());
            json.put("login","success");
            return json.toMap();
            //return "index";
        }
        json.put("login","fail");
        return json.toMap();
        //return "login";
    }

    @PostMapping("logout")
    public Map logout(WebRequest request, SessionStatus status){
        // 우선 호출 후,
        status.setComplete();
        // 세션 속성 수정 가능
        request.removeAttribute("log", WebRequest.SCOPE_SESSION); // scope : 미리 약속돼있는 상수

        JSONObject json = new JSONObject();
        json.put("logout","success");

        return json.toMap();
    }
}
