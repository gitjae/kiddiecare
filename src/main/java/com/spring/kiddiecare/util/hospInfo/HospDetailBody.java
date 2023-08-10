package com.spring.kiddiecare.util.hospInfo;

import com.spring.kiddiecare.util.hospbasis.HospBasisItem;
import lombok.*;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HospDetailBody {
    private List<HospDetailInfo> items;
    private int numOfRows;
    private int pageNo;
    private int totalCount;
}
