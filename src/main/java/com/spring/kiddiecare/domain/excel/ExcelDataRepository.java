package com.spring.kiddiecare.domain.excel;


import org.springframework.data.jpa.repository.JpaRepository;

public interface ExcelDataRepository extends JpaRepository<ExcelData, String> {
    ExcelData findBySubject(String subject);
}
