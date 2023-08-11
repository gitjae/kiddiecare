package com.spring.kiddiecare.util.hospInfo;

import lombok.*;

import javax.xml.bind.annotation.*;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "item")
public class HospDetailItem {
    @XmlElement(name = "plcDir")
    private String plcDir; // 병원 주소
    @XmlElement(name = "trmtSunEnd")
    private String trmtSunEnd; // 일요일 진료 종료 시각
    @XmlElement(name = "rcvSat")
    private String rcvSat; // 토요일 접수 여부
    @XmlElement(name = "trmtSunStart")
    private String trmtSunStart; // 일요일 진료 시작 시각
    @XmlElement(name = "emyNgtTelNo1")
    private String emyNgtTelNo1; // 응급실 밤간 전화번호1
    @XmlElement(name = "emyNgtTelNo2")
    private String emyNgtTelNo2; // 응급실 밤간 전화번호2
    @XmlElement(name = "lunchWeek")
    private String lunchWeek; // 주중 점심시간
    @XmlElement(name = "trmtWedStart")
    private String trmtWedStart; // 수요일 진료 시작 시각
    @XmlElement(name = "trmtWedEnd")
    private String trmtWedEnd; // 수요일 진료 종료 시각
    @XmlElement(name = "trmtThuStart")
    private String trmtThuStart; // 목요일 진료 시작 시각
    @XmlElement(name = "trmtMonStart")
    private String trmtMonStart; // 월요일 진료 시작 시각
    @XmlElement(name = "trmtMonEnd")
    private String trmtMonEnd; // 월요일 진료 종료 시각
    @XmlElement(name = "trmtTueStart")
    private String trmtTueStart; // 화요일 진료 시작 시각
    @XmlElement(name = "trmtTueEnd")
    private String trmtTueEnd; // 화요일 진료 종료 시각
    @XmlElement(name = "lunchSat")
    private String lunchSat; // 토요일 점심시간
    @XmlElement(name = "rcvWeek")
    private String rcvWeek; // 주말 접수 여부
    @XmlElement(name = "trmtSatStart")
    private String trmtSatStart; // 토요일 진료 시작 시각
    @XmlElement(name = "trmtSatEnd")
    private String trmtSatEnd; // 토요일 진료 종료 시각
    @XmlElement(name = "plcNm")
    private String plcNm; // 병원명
    @XmlElement(name = "parkXpnsYn")
    private String parkXpnsYn; // 주차비용 ��부
    @XmlElement(name = "parkEtc")
    private String parkEtc; // 기타 주차정보
    @XmlElement(name = "noTrmtSun")
    private String noTrmtSun; // 일요일 진료 안내
    @XmlElement(name = "noTrmtHoli")
    private String noTrmtHoli; // 공휴일 진료 안내
    @XmlElement(name = "emyDayYn")
    private String emyDayYn; // 응급실 주간 여부
    @XmlElement(name = "emyDayTelNo1")
    private String emyDayTelNo1; // 응급실 주간 전화번호1
    @XmlElement(name = "emyDayTelNo2")
    private String emyDayTelNo2; // 응급실 주간 전화번호2
    @XmlElement(name = "emyNgtYn")
    private String emyNgtYn; // 응급실 밤간 여부
    @XmlElement(name = "trmtThuEnd")
    private String trmtThuEnd; // 목요일 진료 종료 시각
    @XmlElement(name = "trmtFriStart")
    private String trmtFriStart; // 금요일 진료 시작 시각
    @XmlElement(name = "trmtFriEnd")
    private String trmtFriEnd; // 금요일 진료 종료 시각
    @XmlElement(name = "plcDist")
    private String plcDist; // 병원 지역
    @XmlElement(name = "parkQty")
    private String parkQty; // 주차장 정보
}
