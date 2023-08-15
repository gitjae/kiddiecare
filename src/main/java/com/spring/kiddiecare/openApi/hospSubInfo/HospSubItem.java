package com.spring.kiddiecare.openApi.hospSubInfo;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "item")
public class HospSubItem {
    @XmlElement(name = "dgsbjtCd")
    private String dgsbjtCd;
    @XmlElement(name = "dgsbjtCdNm")
    private String dgsbjtCdNm;
    @XmlElement(name = "dgsbjtPrSdrCnt")
    private String dgsbjtPrSdrCnt;
    @XmlElement(name = "cdiagDrCnt")
    private String cdiagDrCnt;
}
