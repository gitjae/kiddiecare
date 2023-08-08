package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.user.User;
import com.spring.kiddiecare.domain.user.UserRepository;
import com.spring.kiddiecare.domain.user.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RequiredArgsConstructor
@SessionAttributes({"log"})
@Controller
public class LogController {
    private final UserRepository userRepository;

    @PostMapping("login")
    public String login(@RequestBody UserRequestDto userDto, Model model){
        Optional<User> user = userRepository.findUserById(userDto.getId());

        if(!user.isEmpty() && userDto.getPassword().equals(user.get().getPassword())){
            model.addAttribute("log", userDto.getId());
            return "index";
        }

        return "login";
    }
}
