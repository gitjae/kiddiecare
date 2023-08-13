package com.spring.kiddiecare.util;


import com.spring.kiddiecare.util.hospInfo.HospDetailBody;
import com.spring.kiddiecare.util.hospInfo.HospDetailItem;
import com.spring.kiddiecare.util.hospInfo.HospDetailResponse;
import com.spring.kiddiecare.util.hospSubInfo.HospSubBody;
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
    private ValueOperations<String, HospBasisBody> valueOps;
    private ValueOperations<String, HospDetailItem> valueOpsDetail;
    private ValueOperations<String, HospSubBody> valueOpsSub;
    private Duration cacheTtl = Duration.ofMinutes(3);
    @Autowired // inner 클래스 위에 있는 변수들을 생성해준다. @Autowired는 원래는 쓰지 않고 @RequiredArgsConstructor 사용
    public OpenApiDataUtil(RedisTemplate redisTemplate) {
        this.restTemplate = new RestTemplate();
        this.redisTemplate = redisTemplate;
        this.valueOps = redisTemplate.opsForValue();
        this.valueOpsDetail = redisTemplate.opsForValue();
        this.valueOpsSub = redisTemplate.opsForValue();
    }

    public HospBasisBody getHospList(String url, String query) {
        // 캐시 데이터 확인하기
        HospBasisBody cachedData = valueOps.get(query);
        if (cachedData != null) {
            return cachedData;
        }

        HospBasisBody resultBody = null;
        try {
            URI reqeustUrl = new URI(url);
            HospBasisResponse data = restTemplate.getForObject(reqeustUrl, HospBasisResponse.class);
            resultBody = data.getBody();
            System.out.println(resultBody);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (resultBody != null) {
            valueOps.set(query, resultBody, cacheTtl);
            return resultBody;
        }

        return null;
    }

    public HospDetailItem getHospData(String url, HospBasisItem item) {
        // 캐시 데이터 확인하기
        HospDetailItem cachedData = valueOpsDetail.get(item.getYadmNm());
        if (cachedData != null) {
            return cachedData;
        }

        HospDetailItem resultBody = null;
        try {
            URI reqeusturl = new URI(url);
            HospDetailResponse data = restTemplate.getForObject(reqeusturl, HospDetailResponse.class);
            System.out.println("data"+data);
            if(data.getBody() != null){
                data.getBody().getItems().getItem().setBasisItem(item);
            }
            resultBody = data.getBody().getItems().getItem();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (resultBody != null) {
            valueOpsDetail.set(item.getYadmNm(), resultBody, cacheTtl);
            return resultBody;
        }

        return null;
    }

    public List<HospSubItem> getHospSubData(String url, String ykiho){
        Optional<HospDetailItem> cachedData = Optional.ofNullable(valueOpsDetail.get(ykiho));
        List<HospSubItem> responseDataList = null;
        if(cachedData.isPresent()){
            System.out.println(cachedData.get());
            responseDataList = cachedData.get().getSubItems();
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
            cachedData.get().setSubItems(responseDataList);
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
