package com.spring.kiddiecare.openApi.hospInfo;

import lombok.*;

import javax.xml.bind.annotation.*;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "header")
public class HospDetailHeader {
    @XmlElement(name = "resultCode")
    private String resultCode;

    @XmlElement(name = "resultMsg")
    private String resultMsg;

}