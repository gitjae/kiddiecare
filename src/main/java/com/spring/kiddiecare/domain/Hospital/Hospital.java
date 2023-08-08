package com.spring.kiddiecare.domain.Hospital;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Hospital {

    @Id
    private String ykiho;

    @Column(nullable = false)
    private String hospitalName;

    @Column
    private String hospitalIntro;

    @Column
    private String hospitalAnnouncement;

    @Column
    private int hospitalMaxSlots;

    @Column
    private int reservesSlots;
}
