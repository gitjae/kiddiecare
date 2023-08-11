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
}
