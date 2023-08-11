package com.spring.kiddiecare.util;

import java.util.Calendar;

import com.spring.kiddiecare.util.hospInfo.HospDetailItem;
import org.springframework.stereotype.Component;

@Component
public class CalenderAndGetTrmtUtil {
    public int printToday() {
        // 현재 시간 가져오기
        Calendar now = Calendar.getInstance();
        // 요일 구하기
        int dayOfWeek = now.get(Calendar.DAY_OF_WEEK);
        // 요일 이름 구하기
        String[] dayNames = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        String displayName = dayNames[dayOfWeek - 1];
        // 출력
        System.out.println("오늘은 " + displayName + "입니다.");

        return dayOfWeek;
    }
//    public static String getLunch(){
//
//    }
    public String getStartByWeekday(HospDetailItem item) {
        System.out.println(item);
        String start = "";
        String end = "";
        switch (printToday()) {
            case 1:
                start = item.getTrmtSunStart();
                end = item.getTrmtSunEnd();
                break;
            case 2:
                start = item.getTrmtMonStart();
                end = item.getTrmtMonEnd();
                break;
            case 3:
                start = item.getTrmtTueStart();
                end = item.getTrmtTueEnd();
                break;
            case 4:
                start = item.getTrmtWedStart();
                end = item.getTrmtWedEnd();
                break;
            case 5:
                start = item.getTrmtThuStart();
                end = item.getTrmtThuEnd();
                break;
            case 6:
                start = item.getTrmtFriStart();
                end = item.getTrmtFriEnd();
                break;
            case 7:
                start = item.getTrmtSatStart();
                end = item.getTrmtSatEnd();
                break;
        }
        return String.format("%s:%s ~ %s:%s",start.substring(0,2),start.substring(2)
                ,end.substring(0,2),end.substring(2));
    }
}

