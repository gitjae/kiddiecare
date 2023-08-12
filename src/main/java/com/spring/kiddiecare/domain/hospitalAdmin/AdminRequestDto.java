package com.spring.kiddiecare.domain.hospitalAdmin;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class AdminRequestDto {
    private String ykiho;
    private String adminId;
    private String adminPw;
    private String adminName;
    private String adminEmail;
    private MultipartFile file;
}
