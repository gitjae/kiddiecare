package com.spring.kiddiecare.openApi.hospSubInfo;

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
public class HospSubResponse {
    @XmlElement(name = "header")
    private HospSubHeader header;

    @XmlElement(name = "body")
    private HospSubBody body;
}
