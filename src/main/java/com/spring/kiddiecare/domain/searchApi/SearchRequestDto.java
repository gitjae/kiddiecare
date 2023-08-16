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
    private String pageNo;
    private String keyword;
    private String xPos;
    private String yPos;
    private String radius;
    private String dgsbjtCd;
    public Map<String, Object> toParameterMap() {
        Map<String, Object> parameterMap = new HashMap<>();

        try{
            parameterMap.put("pageNo", Integer.parseInt(pageNo));
        }catch (Exception e){
            parameterMap.put("pageNo", 1);
        }

        if (keyword != null && !keyword.isEmpty()) {
            parameterMap.put("yadmNm", keyword);
        }

        if (xPos != null && !xPos.isEmpty()) {
            try{
                Double.parseDouble(xPos);
                parameterMap.put("xPos", xPos);
            }catch (Exception e){
                System.out.println(e);
            }
        }

        if (yPos != null && !yPos.isEmpty()) {
            try{
                Double.parseDouble(yPos);
                parameterMap.put("yPos", yPos);
            }catch (Exception e){
                System.out.println(e);
            }
        }

        if (radius != null && !radius.isEmpty()) {
            parameterMap.put("radius", radius);
        }

        if(dgsbjtCd != null && !dgsbjtCd.isEmpty()){
            parameterMap.put("dgsbjtCd", dgsbjtCd);
        }

        return parameterMap;
    }
}
