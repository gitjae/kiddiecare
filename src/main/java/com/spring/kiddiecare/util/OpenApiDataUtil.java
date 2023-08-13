package com.spring.kiddiecare.util;


import com.spring.kiddiecare.util.hospInfo.HospDetailBody;
import com.spring.kiddiecare.util.hospInfo.HospDetailResponse;
import com.spring.kiddiecare.util.hospSubInfo.HospSubBody;
import com.spring.kiddiecare.util.hospSubInfo.HospSubItem;
import com.spring.kiddiecare.util.hospSubInfo.HospSubResponse;
import com.spring.kiddiecare.util.hospbasis.HospBasisBody;
import com.spring.kiddiecare.util.hospbasis.HospBasisResponse;
import com.spring.kiddiecare.util.hospInfo.HospDetailBody;
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
    private ValueOperations<String, HospBasisBody> valueOps;
    private ValueOperations<String, HospDetailBody> valueOpsDetail;
    private ValueOperations<String, HospSubBody> valueOpsSub;
    @Autowired // inner 클래스 위에 있는 변수들을 생성해준다. @Autowired는 원래는 쓰지 않고 @RequiredArgsConstructor 사용
    public OpenApiDataUtil(RedisTemplate redisTemplate) {
        this.restTemplate = new RestTemplate();
        this.redisTemplate = redisTemplate;
        this.valueOps = redisTemplate.opsForValue();
        this.valueOpsDetail = redisTemplate.opsForValue();
        this.valueOpsSub = redisTemplate.opsForValue();
    }

    public HospBasisBody getHospList(String url, String uri, Duration cacheTtl) {
        // 캐시 데이터 확인하기
        HospBasisBody cachedData = valueOps.get(uri);
        if (cachedData != null) {
            return cachedData;
        }

        HospBasisBody resultBody = null;
        try {
            URI reqeustUrl = new URI(url);
            HospBasisResponse data = restTemplate.getForObject(reqeustUrl, HospBasisResponse.class);
            resultBody = data.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (resultBody != null) {
            System.out.println("리스트 정보 받아오기");
            valueOps.set(uri, resultBody, cacheTtl);
            return resultBody;
        }

        return null;
    }

    public HospDetailBody getHospData(String url, String ykiho, Duration cacheTtl) {
        // 캐시 데이터 확인하기
        HospDetailBody cachedData = valueOpsDetail.get(ykiho);
        if (cachedData != null) {
            return cachedData;
        }

        HospDetailBody resultBody = null;
        try {
            URI reqeusturl = new URI(url);
            HospDetailResponse data = restTemplate.getForObject(reqeusturl, HospDetailResponse.class);
            resultBody = data.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (resultBody != null) {
            valueOpsDetail.set(ykiho, resultBody, cacheTtl);
            return resultBody;
        }

        return null;
    }

    public List<HospSubItem> getHospSubData(String url, String ykiho){
        Optional<HospDetailBody> cachedData = Optional.ofNullable(valueOpsDetail.get(ykiho));
        List<HospSubItem> responseDataList = null;
        if(cachedData.isPresent()){
            System.out.println(cachedData.get());
            responseDataList = cachedData.get().getItems().getItem().getSubItems();
            if (responseDataList != null){
                return responseDataList;
            }
        }

        try {
            URI reqeustUrl = new URI(url);
            HospSubResponse data = restTemplate.getForObject(reqeustUrl, HospSubResponse.class);
            int totalCount = data.getBody().getTotalCount();
            if (totalCount > 10){
                url += "&numOfRows="+totalCount;
                data = restTemplate.getForObject(new URI(url), HospSubResponse.class);
            }
            responseDataList = data.getBody().getItems();
            if(responseDataList == null){
                return null;
            }
            cachedData.get().getItems().getItem().setSubItems(responseDataList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (cachedData.isPresent() && responseDataList != null) {
            valueOpsDetail.set(ykiho, cachedData.get());
            return responseDataList;
        }

        return null;
    }
}
