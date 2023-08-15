package com.spring.kiddiecare.domain.children;

import lombok.*;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChildrenRequestDto {
    private int id;     // id

    private int parentNo;
    private String name;
    private int birth;
    private int gender;
    private String info;
}
