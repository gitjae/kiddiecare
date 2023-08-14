package com.spring.kiddiecare.util;


import com.spring.kiddiecare.domain.hospitalInfo.HospData;
import com.spring.kiddiecare.util.hospInfo.HospDetailItem;
import com.spring.kiddiecare.util.hospInfo.HospDetailResponse;
import com.spring.kiddiecare.util.hospSubInfo.HospSubItem;
import com.spring.kiddiecare.util.hospSubInfo.HospSubResponse;
import com.spring.kiddiecare.util.hospbasis.HospBasisBody;
import com.spring.kiddiecare.util.hospbasis.HospBasisItem;
import com.spring.kiddiecare.util.hospbasis.HospBasisResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.Optional;


@Component
public class OpenApiDataUtil {
    private final RestTemplate restTemplate;
    private final RedisTemplate redisTemplate;
    private ValueOperations<String, HospData> valueOps;
    private Duration cacheTtl = Duration.ofMinutes(3);

    @Autowired // inner 클래스 위에 있는 변수들을 생성해준다. @Autowired는 원래는 쓰지 않고 @RequiredArgsConstructor 사용
    public OpenApiDataUtil(RedisTemplate redisTemplate) {
        this.restTemplate = new RestTemplate();
        this.redisTemplate = redisTemplate;
        this.valueOps = redisTemplate.opsForValue();
    }

    public HospData getHospList(String url, String query) {
        Optional<HospData> cacheData = Optional.ofNullable(valueOps.get(query));

        // 캐시 데이터 확인하기
        if (cacheData.isPresent()) {
            return cacheData.get();
        }

        HospData HospData = new HospData();
        try {
            URI reqeustUrl = new URI(url);
            HospBasisResponse data = restTemplate.getForObject(reqeustUrl, HospBasisResponse.class);
            if (data != null) {
                HospData.setHospListData(data.getBody().getItems()); // hospMainData 값 설정
                valueOps.set(query, HospData, cacheTtl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("hospData 확인 " + HospData);
        return HospData;
    }


    public HospData getHospData(String url, String yadmNm) {
        Optional<HospData> cacheData = Optional.ofNullable(valueOps.get(yadmNm));
        // 캐시 데이터 확인
        if (cacheData.isPresent()) {
            return cacheData.get();
        }

        HospData HospData = new HospData();
        try {
            URI reqeustUrl = new URI(url);
            HospDetailResponse data = restTemplate.getForObject(reqeustUrl, HospDetailResponse.class);
            HospDetailItem item = data.getBody().getItems().getItem();
            if(item != null){
                System.out.println("데이터 확인하겠습니다. "+ item);
                HospData.setHospDetailData(item);
                valueOps.set(yadmNm, HospData, cacheTtl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HospData;
    }

    public HospData getHospSubData(String url, String yadmNm){
        boolean isChangedData = false;
        Optional<HospData> cachedData = Optional.ofNullable(valueOps.get(yadmNm));
        if(cachedData.isPresent()){
            List<HospSubItem> hospSubData = cachedData.get().getHospSubData();
            if (hospSubData != null){
                return cachedData.get();
            }
            try {
                URI reqeustUrl = new URI(url);
                System.out.println("보낼 URL 확인"+reqeustUrl);
                Optional<HospSubResponse> data = Optional.ofNullable(
                        restTemplate.getForObject(reqeustUrl, HospSubResponse.class));
                if(data.isPresent()){
                    int totalCount = data.get().getBody().getTotalCount();
                    if (totalCount > 10) {
                        url += "&numOfRows="+totalCount;
                        data = Optional.ofNullable(restTemplate.getForObject(new URI(url), HospSubResponse.class));
                    }
                    List<HospSubItem> subItems = cachedData.get().getHospSubData();
                    if(subItems != null){
                        cachedData.get().setHospSubData(subItems);
                        isChangedData = true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            return null;
        }

        if (isChangedData) {
            valueOps.set(yadmNm, cachedData.get());
            return cachedData.get();
        }

        return cachedData.get();
    }

}
