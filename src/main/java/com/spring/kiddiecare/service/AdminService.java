package com.spring.kiddiecare.service;

import com.spring.kiddiecare.domain.hospitalAdmin.AdminRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class AdminService {
    @Transactional
    public void updateAdminById(String user, AdminRequestDto adminRequestDto){
        
    }
}
