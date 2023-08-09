package com.spring.kiddiecare.service;

import com.spring.kiddiecare.domain.hospitalAdmin.Admin;
import com.spring.kiddiecare.domain.hospitalAdmin.AdminRepository;
import com.spring.kiddiecare.domain.hospitalAdmin.AdminRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class AdminService {
    private final AdminRepository adminRepository;
    @Transactional // 오류 발생 시 rollback 해줄려고 씀
    public void deleteUserByNo(long no){
        adminRepository.deleteById(no);// 아이디 값은 pk이다!!
    }
    @Transactional
    public Admin updateUserByNo(long no, AdminRequestDto adminDto){
        Admin admin = adminRepository.findById(no).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 사용자입니다.")
        );
        admin.Update(adminDto);
        return admin;
    }
}
