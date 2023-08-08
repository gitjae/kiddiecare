package com.spring.kiddiecare.domain.hospitalAdmin;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRespository extends JpaRepository<Admin,Long> {

    public Admin findByAdminIdAndAdminPw(String adminId, String AdminPw);

}
