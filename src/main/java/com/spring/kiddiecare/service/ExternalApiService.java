package com.spring.kiddiecare.service;

import com.spring.kiddiecare.util.ApiResponse;
import com.spring.kiddiecare.util.hospbasis.HospBasisItem;
import com.spring.kiddiecare.util.hospbasis.HospBasisBody;
import com.spring.kiddiecare.util.hospbasis.HospBasisResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.*;
import java.time.Duration;
import java.util.List;

@Service
public class ExternalApiService {
    private final RestTemplate restTemplate;
    private final RedisTemplate redisTemplate;
    private ValueOperations<String, HospBasisResponse> valueOps;

    @Autowired
    public ExternalApiService(RedisTemplate redisTemplate){
        this.restTemplate = new RestTemplate();
        this.redisTemplate = redisTemplate;
        this.valueOps = redisTemplate.opsForValue();
    }


    public ApiResponse fetchData(String url, Duration cacheTtl) {
        JSONObject result = new JSONObject();
        HospBasisResponse cachedDate = valueOps.get(url);
        if (cachedDate != null) {
            return new ApiResponse(cachedDate.toString(), "cache");
        }

        HospBasisResponse data = null;

        try {
            URI uri = new URI(url);
            data = restTemplate.getForObject(uri, HospBasisResponse.class);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        System.out.println("data:" + data);

        if (result != null) {
            valueOps.set(url, data, cacheTtl);
            return new ApiResponse(data.toString(), "API");
        }

        return new ApiResponse(data.toString(), "error");
    }

    public ApiResponse hospList(String url, Duration cacheTtl) {
        JSONObject result = new JSONObject();
        HospBasisResponse cachedDate = valueOps.get(url);
        if (cachedDate != null) {
            return new ApiResponse(cachedDate.toString(), "cache");
        }

        HospBasisResponse data = null;

        try {
            data = restTemplate.getForObject(new URI(url), HospBasisResponse.class);

            JSONObject jsonData = new JSONObject(data);
            int numOfRows = jsonData.getJSONObject("response").getJSONObject("body").getInt("numOfRows");
            int pageNo = jsonData.getJSONObject("response").getJSONObject("body").getInt("pageNo");
            int totalCount = jsonData.getJSONObject("response").getJSONObject("body").getInt("totalCount");

            JSONArray itemList  = jsonData.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");
            for (int i = 0; i < itemList.length(); i++) {
                JSONObject value = new JSONObject();
                JSONObject item = itemList.getJSONObject(i);
                value.put("XPos", item.getDouble("XPos"));
                value.put("YPos", item.getDouble("YPos"));
                value.put("yadmNm", item.getString("yadmNm"));
                value.put("telno", item.getString("telno"));
                value.put("estbDd", item.getInt("estbDd"));
                result.put(item.getString("ykiho"),value);
            }
            result.put("numOfRows",numOfRows);
            result.put("pageNo",pageNo);
            result.put("totalCount",totalCount);

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        System.out.println("data:" + data);

        if (result != null) {
            valueOps.set(url, data, cacheTtl);
            return new ApiResponse(result.toString(), "API");
        }

        return new ApiResponse(data.toString(), "error");
    }

//    public ApiResponse fetchData2(String url,Duration cacheTtl) {
//        StringBuffer result = new StringBuffer();
//        String cachedDate = valueOps.get(url);
//
//        if (cachedDate != null) {
//            return new ApiResponse(cachedDate, "cache");
//        }
//
//        try {
//            URL url1 = new URL(url);
//            HttpURLConnection urlConnection = (HttpURLConnection) url1.openConnection();
//            urlConnection.connect();
//            BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
//            String returnLine;
//
//            while ((returnLine = bufferedReader.readLine()) != null) {
//                result.append(returnLine);
//            }
//
//            // xml 형식의 데이터를 json으로 변환합니다.
//            JSONObject jsonObject = XML.toJSONObject(result.toString());
//
//            // 오류가 있는지 검사합니다.
//            if (jsonObject.toString().contains("SERVICE ERROR")) {
//                return new ApiResponse(jsonObject.toString(), "error");
//            }
//
//            // "items" 항목을 추출합니다.
//            JSONArray itemArr = jsonObject.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");
//
//            JSONObject jsonData = new JSONObject();
//            for (int i = 0; i < itemArr.length(); i++) {
//                JSONObject item = itemArr.getJSONObject(i);
//                JSONObject value = new JSONObject();
//                value.put("XPos", item.getDouble("XPos"));
//                value.put("YPos", item.getDouble("YPos"));
//                value.put("yadmNm", item.getString("yadmNm"));
//                value.put("telno", item.getString("telno"));
//                value.put("estbDd", item.getInt("estbDd"));
//                jsonData.put(item.getString("ykiho"),value);
//            }
////            // 첫 번째 "item"을 추출합니다.
////            JSONObject jsonPrintobj = itemArr.getJSONObject(0);
//
//            // 추출된 데이터를 캐시에 저장합니다.
//            valueOps.set(url, jsonData.toString(), cacheTtl);
//            return new ApiResponse(jsonData.toString(), "API");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ApiResponse("error", "API");
//        }
//    }


    public HospBasisResponse fetchDataClass(String url, Duration cacheTtl) {
        HospBasisResponse cachedData = valueOps.get(url);
        if (cachedData != null) {
            return cachedData;
        }

        HospBasisResponse data = null;

        try {
            URI uri = new URI(url);
            HospBasisResponse response = restTemplate.getForObject(uri, HospBasisResponse.class);
            data = response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("data:" + data);

        if (data != null) {
            valueOps.set(url, data, cacheTtl);
            return data;
        }

        return data;
    }
}
