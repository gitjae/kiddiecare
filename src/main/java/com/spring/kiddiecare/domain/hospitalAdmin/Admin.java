package com.spring.kiddiecare.domain.hospitalAdmin;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.IOException;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "hospital_admin")
@ToString
public class Admin extends AdminTimestamp{
    @Id
    private long no;
    @Column(nullable = false,length = 80)
    private String ykiho;
    @Column(nullable = false,length = 20)
    private String adminId;
    @Column(nullable = false,length = 20)
    private String adminPw;
    @Column(nullable = false,length = 20)
    private String adminName;
    @Column(nullable = false,length = 320)
    private String adminEmail;
    @Lob // 라지 오브젝트라는걸 알려줌 Lombok에 있어용
    @Column(columnDefinition = "BLOB") // Blob이라고 알려줌
    private byte[] file;

    public Admin(AdminRequestDto adminDto){
        this.no = 0L;
        this.ykiho = adminDto.getYkiho();
        this.adminId = adminDto.getAdminId();
        this.adminPw = adminDto.getAdminPw();
        this.adminName = adminDto.getAdminName();
        this.adminEmail = adminDto.getAdminEmail();
        if(adminDto.getFile() != null) {
            try {
                this.file = adminDto.getFile().getBytes();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public void Update(AdminRequestDto adminDto){
        this.adminPw = adminDto.getAdminPw();
        this.adminName = adminDto.getAdminName();
        this.adminEmail = adminDto.getAdminEmail();
    }
}
