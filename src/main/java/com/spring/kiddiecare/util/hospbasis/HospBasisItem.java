package com.spring.kiddiecare.util.hospbasis;

import com.spring.kiddiecare.util.hospInfo.HospDetailItem;
import com.spring.kiddiecare.util.hospInfo.HospDetailItems;
import lombok.*;

import javax.xml.bind.annotation.*;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "item")
public class HospBasisItem {

    @XmlElement(name = "addr")
    private String addr; // 주소

    @XmlElement(name = "clCd")
    private String clCd; // 종별코드

    @XmlElement(name = "clCdNm")
    private String clCdNm; // 종별코드명

//    @XmlElement(name = "cmdcGdrCnt")
//    private String cmdcGdrCnt; // 한방일반의 인원수
//
//    @XmlElement(name = "cmdcIntnCnt")
//    private String cmdcIntnCnt; // 한방인턴 인원수
//
//    @XmlElement(name = "cmdcResdntCnt")
//    private String cmdcResdntCnt; // 한방레지던트 인원수
//
//    @XmlElement(name = "cmdcSdrCnt")
//    private String cmdcSdrCnt; // 한방전문의 인원수
//
//    @XmlElement(name = "detyGdrCnt")
//    private String detyGdrCnt; // 치과일반의 인원수
//
//    @XmlElement(name = "detyIntnCnt")
//    private String detyIntnCnt; // 치과인턴 인원수
//
//    @XmlElement(name = "detyResdntCnt")
//    private String detyResdntCnt; // 치과레지던트 인원수
//
//    @XmlElement(name = "detySdrCnt")
//    private String detySdrCnt; // 치과전문의 인원수

    @XmlElement(name = "distance")
    private String distance; // 거리

    @XmlElement(name = "drTotCnt")
    private String drTotCnt; // 의사 총 수

    @XmlElement(name = "emdongNm")
    private String emdongNm; // 읍면동명

    @XmlElement(name = "estbDd")
    private String estbDd; // 개설일자

    @XmlElement(name = "hospUrl")
    private String hospUrl; // 홈페이지

    @XmlElement(name = "mdeptGdrCnt")
    private String mdeptGdrCnt; // 의과일반의 인원수

    @XmlElement(name = "mdeptIntnCnt")
    private String mdeptIntnCnt; // 의과인턴 인원수

    @XmlElement(name = "mdeptResdntCnt")
    private String mdeptResdntCnt; // 의과레지던트 인원수

    @XmlElement(name = "mdeptSdrCnt")
    private String mdeptSdrCnt; // 의과전문의 인원수

//    @XmlElement(name = "pnursCnt")
//    private String pnursCnt; // 조산사 인원수

    @XmlElement(name = "postNo")
    private String postNo; // 우편번호

    @XmlElement(name = "sgguCd")
    private String sgguCd; // 시군구코드

    @XmlElement(name = "sgguCdNm")
    private String sgguCdNm; // 시군구명

    @XmlElement(name = "sidoCd")
    private String sidoCd; // 시도코드

    @XmlElement(name = "sidoCdNm")
    private String sidoCdNm; // 시도명

    @XmlElement(name = "telno")
    private String telno; // 전화번호

    @XmlElement(name = "XPos")
    private String xPos; // x좌표

    @XmlElement(name = "YPos")
    private String yPos; // y좌표

    @XmlElement(name = "yadmNm")
    private String yadmNm; // 병원명

    @XmlElement(name = "ykiho")
    private String ykiho; // 암호화된 요양기호
}
