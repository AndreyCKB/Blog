package com.example.rest.spring.blog.util;

import com.example.rest.spring.blog.exception.ErrorMessageForUserException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateConverter {
//Переделать через map и добавить время

    private DateConverter() {
    }

    public  static Date stringInFormat_yyyy_MM_dd_toDate(String str)  {
        if (str == null) {
            throw new ErrorMessageForUserException("Дата не введена");
        }

        String dateFormat = "yyyy-MM-dd";
        if (str.length()==10 && !Character.isDigit(str.charAt(4))) {
            str = str.replaceAll(""+str.charAt(4), "-");
        } else if (str.length() == 8) {
            dateFormat = "yyyyMMdd";
        }
        Date result;
        try {
           result = new SimpleDateFormat(dateFormat).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new ErrorMessageForUserException("Дата введена в недоступном формате. Доступен формат год, месяц, число все цифрами(Например 2000-02-18) с любым разделителем или без разделителя. ");
        }
        return result;
    }


    public  static String dateToString(Date date){
        if (date == null) {
            throw new ErrorMessageForUserException("Дата не введена");
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    public  static String dateAndTimeToString(Date date){
        if (date == null) {
            throw new ErrorMessageForUserException("Дата не введена");
        }
        DateFormat df = new SimpleDateFormat("dd MMMMM yyyy  HH:mm");
        return df.format(date);
    }

    public  static String dateToStringMonthInWords(Date date){
        if (date == null) {
            throw new ErrorMessageForUserException("Дата не введена");
        }
        DateFormat df = new SimpleDateFormat("dd MMMMM yyyy");
        return df.format(date);
    }

//    public static void main(String... args){
//        DateConverter dateConverter = new DateConverter();
//        String s = dateConverter.dateToStringMonthInWords(new Date());
//        System.out.println(s);
//    }
}
