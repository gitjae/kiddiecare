package com.spring.kiddiecare.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity
public class User {
    @Id
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

    public User(UserRequestDto userDto){
        this.id = userDto.getId();
        this.password = userDto.getPassword();
        this.name = userDto.getName();
        this.birth = userDto.getBirth();
        this.gender = userDto.isGender();
        this.phone = userDto.getPhone();
        this.email = userDto.getEmail();
        this.isValid = true;
    }

    public void update(UserRequestDto userDto){
        this.password = userDto.getPassword();
        this.phone = userDto.getPhone();
        this.email = userDto.getEmail();
    }
}
