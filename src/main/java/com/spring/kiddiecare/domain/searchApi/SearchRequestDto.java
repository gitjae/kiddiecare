package com.spring.kiddiecare.domain.searchApi;

import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class SearchRequestDto {
    private String PageNo;
    private String keyword;
    private String xPos;
    private String yPos;
    private String radius;
    public Map<String, Object> toParameterMap() {
        Map<String, Object> parameterMap = new HashMap<>();

        try{
            parameterMap.put("PageNo", Integer.parseInt(PageNo));
        }catch (Exception e){
            parameterMap.put("PageNo", 1);
        }

        if (keyword != null && !keyword.isEmpty()) {
            parameterMap.put("keyword", keyword);
        }


        if (xPos != null && !xPos.isEmpty()) {
            parameterMap.put("xPos", xPos);
        }

        if (yPos != null && !yPos.isEmpty()) {
            parameterMap.put("yPos", yPos);
        }

        if (radius != null && !radius.isEmpty()) {
            parameterMap.put("radius", radius);
        }

        return parameterMap;
    }
}
