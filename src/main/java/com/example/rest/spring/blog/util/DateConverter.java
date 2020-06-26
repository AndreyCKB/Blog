package com.example.rest.spring.blog.util;

import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateConverter {
//Переделать через map и добавить время
    public  Date stringInFormat_yyyy_MM_dd_toDate(String str)  {
        if (str == null) return null;

        String dateFormat = "yyyy-MM-dd";
        if (str.length()==10 && str.contains("_")) {
            dateFormat = "yyyy_MM_dd";
        } else if (str.length() == 8) {
            dateFormat = "yyyyMMdd";
        }

        Date result;
        try {
           return result = new SimpleDateFormat(dateFormat).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public  String dateToStringInFormat_yyyy_MM_dd(Date date){
        if (date == null) return null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }
}
