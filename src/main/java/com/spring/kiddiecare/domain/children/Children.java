package com.spring.kiddiecare.domain.children;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Children {
    @Id
    private int no;     // id

    private int parentNo;
    private String name;
    private int birth;
    private int gender;
    private String info;
}
