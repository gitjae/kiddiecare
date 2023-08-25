package com.spring.kiddiecare.util;


import com.spring.kiddiecare.domain.hospitalInfo.HospData;
import com.spring.kiddiecare.openApi.hospInfo.HospDetailItem;
import com.spring.kiddiecare.openApi.hospInfo.HospDetailResponse;
import com.spring.kiddiecare.openApi.hospSubInfo.HospSubItem;
import com.spring.kiddiecare.openApi.hospSubInfo.HospSubResponse;
import com.spring.kiddiecare.openApi.hospbasis.HospBasisItem;
import com.spring.kiddiecare.openApi.hospbasis.HospBasisResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Component
public class OpenApiDataUtil {
    private final RestTemplate restTemplate;
    private final RedisTemplate redisTemplate;
    private ValueOperations<String, HospData> valueOps;
    private ValueOperations<String,String> emailOps;
    private Duration cacheTtl = Duration.ofMinutes(180);

    @Autowired // inner 클래스 위에 있는 변수들을 생성해준다. @Autowired는 원래는 쓰지 않고 @RequiredArgsConstructor 사용
    public OpenApiDataUtil(RedisTemplate redisTemplate) {
        this.restTemplate = new RestTemplate();
        this.redisTemplate = redisTemplate;
        this.valueOps = redisTemplate.opsForValue();
        this.emailOps = redisTemplate.opsForValue();
    }

    public HospData getHospList(String url, String query) {

        // 캐시 데이터 확인하기
        Optional<HospData> cacheData = Optional.ofNullable(valueOps.get(query));
        if (cacheData.isPresent()) {
            return cacheData.get();
        }

        HospData HospData = new HospData();
        try {
            URI reqeustUrl = new URI(url);
            HospBasisResponse data = restTemplate.getForObject(reqeustUrl, HospBasisResponse.class);
            if (data != null) {
                List<HospBasisItem> hostList = data.getBody().getItems();
                HospData.setHospListData(hostList);
                // hospMainData 값 설정
                valueOps.set(query, HospData, cacheTtl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        System.out.println("hospData 확인 " + HospData);
        return HospData;
    }

    public HospData getHospSubData(String url, String yadmNm){
        // 캐시 데이터 확인
        Optional<HospData> cachedData = Optional.ofNullable(valueOps.get(yadmNm));
        System.out.println("cache"+cachedData);
        if(cachedData.isPresent()){
            List<HospSubItem> hospSubData = cachedData.get().getHospSubData();
            if(hospSubData != null){
                return cachedData.get();
            }
            try {
                URI reqeustUrl = new URI(url);
                Optional<HospSubResponse> data = Optional.ofNullable(
                        restTemplate.getForObject(reqeustUrl, HospSubResponse.class));
                System.out.println("진료데이터 확인 "+data);
                // 진료 과목 데이터를 확인
                if(data.isPresent()){
                    int totalCount = data.get().getBody().getTotalCount();
                    // 진료과목 데이터에서 과목이 10개 이상이라면 다시요청
                    if (totalCount > 10) {
                        url += "&numOfRows="+totalCount;
                        data = Optional.ofNullable(restTemplate.getForObject(new URI(url), HospSubResponse.class));
                        System.out.println("정보다시 불러옴");
                    }
                    // data가 있다면
                    data.ifPresent(hospSubResponse -> cachedData.get().setHospSubData(hospSubResponse.getBody().getItems()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 캐시 데이터 안에 진료과목이 없으면 url로 요청

            valueOps.set(yadmNm, cachedData.get());
            return valueOps.get(yadmNm);
        }

        return null;
    }

    public void saveEmailAuthToken(String targetEmail, String authToken) {
        Duration duration = Duration.ofMinutes(3);
        emailOps.set(targetEmail,authToken,duration);
    }

    public String getEmailAuthToken(String targetEmail){
        return emailOps.get(targetEmail);
    }

}
