package com.spring.kiddiecare.domain.hospitalInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("hospitalInfo")
public class HospitalInfo {
    private String ykiho;
    private String yadmNm;
}
