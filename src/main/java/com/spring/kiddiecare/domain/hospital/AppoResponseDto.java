package com.spring.kiddiecare.domain.hospital;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "appoView")
@Entity
public class AppoResponseDto {

    @Id
    private String appoNo;
    private String guardianName;
    private String patientName;
    private int appoStatus;
    private String note;
    private String symptom;
    @Column(nullable = false)
    private int slotNo;
}
