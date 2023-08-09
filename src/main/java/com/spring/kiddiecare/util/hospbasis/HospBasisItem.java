package com.spring.kiddiecare.util.hospbasis;

import lombok.*;

import javax.xml.bind.annotation.*;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "item")
public class HospBasisItem {
    @XmlElement(name = "addr")
    private String addr;

    @XmlElement(name = "clCd")
    private String clCd;

    @XmlElement(name = "clCdNm")
    private String clCdNm;

    @XmlElement(name = "cmdcGdrCnt")
    private int cmdcGdrCnt;

    @XmlElement(name = "cmdcIntnCnt")
    private int cmdcIntnCnt;

    @XmlElement(name = "cmdcResdntCnt")
    private int cmdcResdntCnt;

    @XmlElement(name = "cmdcSdrCnt")
    private int cmdcSdrCnt;

    @XmlElement(name = "detyGdrCnt")
    private int detyGdrCnt;

    @XmlElement(name = "detyIntnCnt")
    private int detyIntnCnt;

    @XmlElement(name = "detyResdntCnt")
    private int detyResdntCnt;

    @XmlElement(name = "detySdrCnt")
    private int detySdrCnt;

    @XmlElement(name = "distance")
    private double distance;

    @XmlElement(name = "drTotCnt")
    private int drTotCnt;

    @XmlElement(name = "emdongNm")
    private String emdongNm;

    @XmlElement(name = "estbDd")
    private String estbDd;

    @XmlElement(name = "mdeptGdrCnt")
    private int mdeptGdrCnt;

    @XmlElement(name = "mdeptIntnCnt")
    private int mdeptIntnCnt;

    @XmlElement(name = "mdeptResdntCnt")
    private int mdeptResdntCnt;

    @XmlElement(name = "mdeptSdrCnt")
    private int mdeptSdrCnt;

    @XmlElement(name = "pnursCnt")
    private int pnursCnt;

    @XmlElement(name = "postNo")
    private String postNo;

    @XmlElement(name = "sgguCd")
    private String sgguCd;

    @XmlElement(name = "sgguCdNm")
    private String sgguCdNm;

    @XmlElement(name = "sidoCd")
    private String sidoCd;

    @XmlElement(name = "sidoCdNm")
    private String sidoCdNm;

    @XmlElement(name = "telno")
    private String telno;

    @XmlElement(name = "XPos")
    private double xPos;

    @XmlElement(name = "YPos")
    private double yPos;

    @XmlElement(name = "yadmNm")
    private String yadmNm;

    @XmlElement(name = "ykiho")
    private String ykiho;
}
