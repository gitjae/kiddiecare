package com.spring.kiddiecare.util;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.time.LocalTime;

public class DateParsor {

    public static Date parse(String source) {
        Date date = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            date = new Date(sdf.parse(source).getTime());
            System.out.println("date : " + date);
        } catch(ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Time convertHourToTime(int hour) {
        LocalTime localTime = LocalTime.of(hour, 0);
        return Time.valueOf(localTime);
    }
}
