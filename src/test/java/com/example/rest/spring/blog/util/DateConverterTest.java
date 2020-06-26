package com.example.rest.spring.blog.util;

import org.junit.jupiter.api.Test;



import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


class DateConverterTest {
    private DateConverter dateConverter;

    public DateConverterTest(DateConverter dateConverter) {
        this.dateConverter = dateConverter;
    }

    @Test
    void stringInFormat_yyyy_MM_dd_toDateTest() throws ParseException {
        String str = "1985-01-01";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
        assertEquals(date, this.dateConverter.stringInFormat_yyyy_MM_dd_toDate(str));

        str = "1985_01_01";
        date = new SimpleDateFormat("yyyy_MM_dd").parse(str);
        assertEquals(date, this.dateConverter.stringInFormat_yyyy_MM_dd_toDate(str));

        str = "19850101";
        date = new SimpleDateFormat("yyyyMMdd").parse(str);
        assertEquals(date, this.dateConverter.stringInFormat_yyyy_MM_dd_toDate(str));
    }

    @Test
    void dateToStringInFormat_yyyy_MM_ddTest() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        assertEquals(df.format(date), this.dateConverter.dateToStringInFormat_yyyy_MM_dd(date));
    }
}