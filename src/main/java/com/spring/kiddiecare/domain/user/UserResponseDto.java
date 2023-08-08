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
    private String email;
    private String token;
    private boolean isValid;
}
