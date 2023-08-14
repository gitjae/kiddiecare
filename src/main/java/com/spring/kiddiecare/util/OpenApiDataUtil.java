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
    private ValueOperations<String, HospBasisItem> valueOpsDetail;
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

        HospBasisBody resultBody = null;

        // 캐시 데이터 확인하기
        HospBasisBody cachedData = valueOps.get(query);
        if (cachedData != null) {
            return cachedData;
        }

        try {
            URI reqeustUrl = new URI(url);
            HospBasisResponse data = restTemplate.getForObject(reqeustUrl, HospBasisResponse.class);
            resultBody = data.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("resultBody 확인 "+resultBody);

        if (resultBody != null) {
            valueOps.set(query, resultBody, cacheTtl);
            return resultBody;
        }

        return null;
    }

    public HospBasisItem getHospData(String url, HospBasisItem item) {

        // 캐시 데이터 확인하기
        HospBasisItem cachedData = valueOpsDetail.get(item.getYadmNm());
        if (cachedData != null) {
            return cachedData;
        }

        HospDetailItem resultBody = null;
        try {
            URI reqeustUrl = new URI(url);
            HospDetailResponse data = restTemplate.getForObject(reqeustUrl, HospDetailResponse.class);
            System.out.println("상세정보 불러오기 "+data);
            HospDetailItem dataItem = data.getBody().getItems().getItem();
            if(dataItem != null){
                item.setHospDetail(dataItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (item != null) {
            valueOpsDetail.set(item.getYadmNm(), item, cacheTtl);
            return item;
        }

        return null;
    }

    public HospBasisItem getHospSubData(String url, String yadmNm){
        boolean isChangedData = false;
        Optional<HospBasisItem> cachedData = Optional.ofNullable(valueOpsDetail.get(yadmNm));
        if(cachedData.isPresent()){
            if (cachedData.get().getSubItems() != null){
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
                    List<HospSubItem> subItems = data.get().getBody().getItems();
                    if(subItems != null){
                        cachedData.get().setSubItems(subItems);
                        isChangedData = true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{

        }

        // cached 데이터는 있지만 서브는 없는 데이터
        // cached 데이터는 없는 데이터



        if (isChangedData) {
            valueOpsDetail.set(yadmNm, cachedData.get());
            return cachedData.get();
        }

        return cachedData.get();
    }

}
