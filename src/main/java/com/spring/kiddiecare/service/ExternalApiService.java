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
    private final StringRedisTemplate redisTemplate;
    private ValueOperations<String, String> valueOps;

    @Autowired
    public ExternalApiService(StringRedisTemplate redisTemplate){
        this.restTemplate = new RestTemplate();
        this.redisTemplate = redisTemplate;
        this.valueOps = redisTemplate.opsForValue();
    }


    public ApiResponse fetchData(String url, Duration cacheTtl) {
        JSONObject result = new JSONObject();
        String cachedDate = valueOps.get(url);
        if (cachedDate != null) {
            return new ApiResponse(cachedDate, "cache");
        }

        String data = null;

        try {
            URI uri = new URI(url);
            data = restTemplate.getForObject(uri, String.class);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        System.out.println("data:" + data);

        if (result != null) {
            valueOps.set(url, data, cacheTtl);
            return new ApiResponse(data, "API");
        }

        return new ApiResponse(data, "error");
    }

    public ApiResponse hospList(String url, Duration cacheTtl) {
        JSONObject result = new JSONObject();
        String cachedDate = valueOps.get(url);
        if (cachedDate != null) {
            return new ApiResponse(cachedDate, "cache");
        }

        String data = null;

        try {
            data = restTemplate.getForObject(new URI(url), String.class);

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
            valueOps.set(url, result.toString(), cacheTtl);
            return new ApiResponse(result.toString(), "API");
        }

        return new ApiResponse(data, "error");
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


    public ApiResponse fetchDataClass(String url, Duration cacheTtl) {
        String cachedData = valueOps.get(url);
        if (cachedData != null) {
            return new ApiResponse(cachedData, "cache");
        }

        String data = null;

        try {
            URI uri = new URI(url);
            HospBasisResponse response = restTemplate.getForObject(uri, HospBasisResponse.class);
            System.out.println(response);
            HospBasisBody body = response.getBody();
            List<HospBasisItem> items = body.getItems();
            for(HospBasisItem item : items){
                System.out.println("item : " + item.getYadmNm());
            }
            JSONArray jsonArray = new JSONArray(items);
            data = jsonArray.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("data:" + data);

        if (data != null) {
            valueOps.set(url, data, cacheTtl);
            return new ApiResponse(data, "API");
        }

        return new ApiResponse(data, "error");
    }
}
