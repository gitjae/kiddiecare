package com.spring.kiddiecare.domain.excel;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity(name = "medical_subject")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ExcelData {
    @Id
    private String no;
    private String subject;
}
