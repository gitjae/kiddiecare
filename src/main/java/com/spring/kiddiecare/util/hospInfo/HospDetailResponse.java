package com.spring.kiddiecare.util.hospInfo;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class HospDetailResponse {
    @XmlElement(name = "header")
    private HospDetailHeader header;

    @XmlElement(name = "body")
    private HospDetailBody body;
}
