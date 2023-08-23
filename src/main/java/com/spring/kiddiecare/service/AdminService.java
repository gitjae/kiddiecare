package com.spring.kiddiecare.service;

import com.spring.kiddiecare.domain.hospital.Hospital;
import com.spring.kiddiecare.domain.hospital.HospitalRepository;
import com.spring.kiddiecare.domain.hospitalAdmin.Admin;
import com.spring.kiddiecare.domain.hospitalAdmin.AdminRepository;
import com.spring.kiddiecare.domain.hospitalAdmin.AdminRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final HospitalRepository hospitalRepository;
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void joinAdminUserByAdmin(Admin admin){
        adminRepository.save(admin);
    }

    @Transactional
    public void joinAdminUserAndHospital(Admin admin, Hospital hospital){
        hospitalRepository.save(hospital);

        adminRepository.save(admin);
    }
    @Transactional // 오류 발생 시 rollback 해줄려고 씀
    public void deleteAdminByNo(long no){
        adminRepository.deleteById(no);// 아이디 값은 pk이다!!
    }
    @Transactional
    public Admin updateAdminByAdminDto(AdminRequestDto adminDto, String session){
        Admin admin = adminRepository.findByAdminId(session);
        admin.Update(adminDto);
        return admin;
    }

}
