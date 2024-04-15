package com.almaz.almazbank.utilities;


import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateTimeUtility {
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//    public java.sql.Date parseDate(String date) {
//        try {
//            return new Date(DATE_FORMAT.parse(date).getTime());
//        } catch (ParseException e) {
//            throw new IllegalArgumentException(e);
//        }
//    }
    public java.util.Date parseDateUtil(String date) {
        try {
            return DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
