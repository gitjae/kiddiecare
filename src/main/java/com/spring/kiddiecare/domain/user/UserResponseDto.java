package com.spring.kiddiecare.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private int no;
    private String id;
    private String password;
    private String name;
    private int birth;
    private boolean gender;
    private int phone;
    // private String phone;
    private String email;
    private String postcode;
    private String addr;
    private String addr_detail;
    private double xpos;
    private double ypos;
    private String token;
    private boolean isValid;

    public UserResponseDto(User user){
        this.no = user.getNo();
        this.id = user.getId();
        this.password = user.getPassword();
        this.name = user.getName();
        this.birth = user.getBirth();
        this.gender = user.isGender();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.postcode = user.getPostcode();
        this.addr = user.getAddr();
        this.addr_detail = user.getAddr_detail();
        this.xpos = user.getXpos();
        this.ypos = user.getYpos();
        this.token = user.getToken();
        this.isValid = user.isValid();
    }
}
