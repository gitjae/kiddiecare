package com.spring.kiddiecare.domain.children;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChildrenResponseDto {
    private int no;     // id
    private int parentNo;
    private String name;
    private int birth;
    private int gender;
    private String info;

    public ChildrenResponseDto(Children child){
        this.no = child.getNo();
        this.parentNo = child.getParentNo();
        this.name = child.getName();
        this.birth = child.getBirth();
        this.gender = child.getGender();
        this.info = child.getInfo();
    }
}
