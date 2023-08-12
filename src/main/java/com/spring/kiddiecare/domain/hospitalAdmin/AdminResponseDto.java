package com.spring.kiddiecare.domain.hospitalAdmin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class AdminResponseDto {
    private long no;
    private String ykiho;
    private String adminId;
    private String adminPw;
    private String adminName;
    private String adminEmail;
    private byte[] file;
}
