package com.spring.kiddiecare.util;

import com.spring.kiddiecare.domain.children.Children;
import com.spring.kiddiecare.domain.doctor.Doctor;
import com.spring.kiddiecare.domain.hospital.Appointment;
import com.spring.kiddiecare.domain.hospital.Hospital;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppoView {
    private int no;
    private String hospital;
    private Date date;
    private Time time;
    private String doctor;
    private int appoStatus;
    private String name;
    private String symptom;
    private String note;

    public AppoView(Appointment appo, Hospital hospital, TimeSlotsLimit timeSlot, Doctor doctor, Children child){
        this.no = appo.getNo();
        this.hospital = hospital.getHospitalName();
        this.date = timeSlot.getDate();
        this.time = timeSlot.getTime();
        this.doctor = doctor.getDoctorName();
        this.appoStatus = appo.getAppoStatus();
        this.name = child.getName();
        this.symptom = appo.getSymptom();
        this.note = appo.getNote();
    }
}
