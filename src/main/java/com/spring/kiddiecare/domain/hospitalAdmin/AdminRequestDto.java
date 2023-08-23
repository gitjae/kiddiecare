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
    private String updateAdminPw;
    private String adminEmail;
    private String adminName;
    private String doctorName;
    private MultipartFile file;

    private String hospitalName;
}
