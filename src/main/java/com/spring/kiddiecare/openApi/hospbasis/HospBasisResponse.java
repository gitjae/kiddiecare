package com.spring.kiddiecare.openApi.hospbasis;

import lombok.*;

import javax.xml.bind.annotation.*;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class HospBasisResponse {

    @XmlElement(name = "header")
    private HospBasisHeader header;

    @XmlElement(name = "body")
    private HospBasisBody body;

}
