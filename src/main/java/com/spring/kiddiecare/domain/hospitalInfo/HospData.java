package com.spring.kiddiecare.domain.hospitalInfo;

import com.spring.kiddiecare.openApi.hospInfo.HospDetailItem;
import com.spring.kiddiecare.openApi.hospSubInfo.HospSubItem;
import com.spring.kiddiecare.openApi.hospbasis.HospBasisItem;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HospData {
    private List<HospBasisItem> hospListData;
    private HospDetailItem hospDetailData;
    private List<HospSubItem> hospSubData;
    private HospBasisItem hospBasisData;
}
