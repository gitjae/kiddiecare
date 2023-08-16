package com.spring.kiddiecare.domain.user;

import com.spring.kiddiecare.util.KakaoMapClient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

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
    private String postcode;
    private String addr;
    private String addr_detail;
    private double xpos;
    private double ypos;
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
        this.postcode = userDto.getPostcode();
        this.addr = userDto.getAddr();

        setCoord(this.addr);

        this.addr_detail = userDto.getAddr_detail();
        this.isValid = true;
    }

    public void update(UserRequestDto userDto){
        if(userDto.getPassword() != null)
            this.password = userDto.getPassword();
        if(userDto.getPhone() != 0)
            this.phone = userDto.getPhone();
        if(userDto.getEmail() != null)
            this.email = userDto.getEmail();
        if(userDto.getPostcode() != null)
            this.postcode = userDto.getPostcode();
        if(userDto.getAddr() != null){
            this.addr = userDto.getAddr();

            setCoord(this.addr);
        }

        if(userDto.getAddr_detail() != null)
            this.addr_detail = userDto.getAddr_detail();
    }

    private void setCoord(String addr){
        KakaoMapClient kakaoMapClient = new KakaoMapClient();
        String res = kakaoMapClient.geocodeAddress(addr);
        String[] coord = res.split("/");

        try {
            this.xpos = Double.parseDouble(coord[0]);
            this.ypos = Double.parseDouble(coord[1]);
            System.out.println(this.xpos);
            System.out.println(this.ypos);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
