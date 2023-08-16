package com.spring.kiddiecare.domain.hospital;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AppoAdminDetailRepository extends JpaRepository<AppoAdminDetailDto, String> {
    @Query(nativeQuery = true, value = "SELECT * FROM appoDetailView WHERE hosp_appo_no=?1")
    public AppoAdminDetailDto findByHospAppoNo(String hospAppoNo);
}
