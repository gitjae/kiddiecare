package com.spring.kiddiecare.util.hospInfo;

import lombok.*;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HospDetailInfoResponse {
    private HospDetailHeader header;
    private HospDetailBody body;
}
