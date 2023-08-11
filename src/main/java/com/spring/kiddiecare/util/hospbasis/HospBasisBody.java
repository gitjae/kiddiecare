package com.spring.kiddiecare.util.hospbasis;

import com.spring.kiddiecare.util.hospInfo.HospDetailItem;
import lombok.*;

import javax.xml.bind.annotation.*;
import java.util.List;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "body")
public class HospBasisBody {
    @XmlElementWrapper(name = "items")
    @XmlElement(name = "item")
    private List<HospBasisItem> items;

    @XmlElement(name = "numOfRows")
    private int numOfRows;

    @XmlElement(name = "pageNo")
    private int pageNo;

    @XmlElement(name = "totalCount")
    private int totalCount;
}
