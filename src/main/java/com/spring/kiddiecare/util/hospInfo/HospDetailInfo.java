package com.spring.kiddiecare.util.hospInfo;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HospDetailInfo {
    private String plcDir;
    private String trmtSunEnd;
    private String rcvSat;
    private String trmtSunStart;
    private String emyNgtTelNo1;
    private String emyNgtTelNo2;
    private String lunchWeek;
    private String trmtWedStart;
    private String trmtWedEnd;
    private String trmtThuStart;
    private String trmtMonStart;
    private String trmtMonEnd;
    private String trmtTueStart;
    private String trmtTueEnd;
    private String lunchSat;
    private String rcvWeek;
    private String trmtSatStart;
    private String trmtSatEnd;
    private String plcNm;
    private String parkXpnsYn;
    private String parkEtc;
    private String noTrmtSun;
    private String noTrmtHoli;
    private String emyDayYn;
    private String emyDayTelNo1;
    private String emyDayTelNo2;
    private String emyNgtYn;
    private String trmtThuEnd;
    private String trmtFriStart;
    private String trmtFriEnd;
    private String plcDist;
    private String parkQty;
}
