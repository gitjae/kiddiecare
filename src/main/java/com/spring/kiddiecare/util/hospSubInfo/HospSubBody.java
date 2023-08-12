package com.spring.kiddiecare.util.hospSubInfo;

import com.spring.kiddiecare.util.hospbasis.HospBasisItem;
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
public class HospSubBody {
    @XmlElementWrapper(name = "items")
    @XmlElement(name = "item")
    private List<HospSubItem> items;

    @XmlElement(name = "numOfRows")
    private int numOfRows;

    @XmlElement(name = "pageNo")
    private int pageNo;

    @XmlElement(name = "totalCount")
    private int totalCount;
}
