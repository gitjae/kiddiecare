package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.hospital.AppoRepository;
import com.spring.kiddiecare.domain.hospital.AppoResponseDto;
import com.spring.kiddiecare.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api/v1/mypage")
@RestController
public class AppoViewController {

    private final UserRepository userRepository;
    private final AppoRepository appoRepository;



}
