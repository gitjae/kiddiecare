package com.spring.kiddiecare.domain.hospital;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name="appoDetailView")
@Entity
public class AppoAdminDetailDto {
    @Id
    private String hospAppoNo;
    private int childrenId;
    private String childrenName;
    private int childrenBirth;
    private int childrenGender;
    private String childrenInfo;
    private int usersNo;
    private String usersName;
    private int usersBirth;
    private String usersAddr;

}
