package com.almaz.almazbank.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public interface createNewUser {
    private static String getSurNameNameMiddle(String surname, String name, String middle_name){
        return String.format("%s %s %s", surname,name,middle_name);
    }
    static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static java.util.Date parseDate(String date) {
        try {
            return DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
