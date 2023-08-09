package com.spring.kiddiecare.util;

import com.spring.kiddiecare.util.hospbasis.HospBasisResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private String data;
    private String source;
}
