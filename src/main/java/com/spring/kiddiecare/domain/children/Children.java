package com.spring.kiddiecare.domain.children;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "children")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Children {
    @Id
    private int id;     // id

    private int parentNo;
    private String name;
    private int birth;
    private int gender;
    private String info;

    public Children(ChildrenRequestDto childDto, int parentNo){
        this.parentNo = parentNo;
        this.name = childDto.getName();
        this.birth = childDto.getBirth();
        this.gender = childDto.getGender();
        this.info = childDto.getInfo();
    }
}
