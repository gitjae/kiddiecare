package com.spring.kiddiecare.domain.hospitalAdmin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class AdminRequestDto {
    private String ykiho;
    private String adminId;
    private String adminPw;
    private String adminName;
    private String adminEmail;
    private byte[] file;
}
