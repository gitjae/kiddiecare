package com.spring.kiddiecare.domain.hospitalAdmin;


import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {
    public Admin findByAdminIdAndAdminPw(String adminId, String AdminPw);
    public Admin findByadminNameAndAdminPw(String adminName,String AdminPw);
    public Admin findByAdminId(String adminId);
}
