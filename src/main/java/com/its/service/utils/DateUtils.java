package com.its.service.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Objects;

import static java.util.Objects.isNull;

public class DateUtils {

    public static final String DATE_TIME_FORMAT = "dd/MM/yyyy hh:mm a";
    public static final String FORMAT_DDMMYYY = "dd/MM/yyyy";
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    public static String format(Date date, String format) {
        return isNull(date) ? null : new SimpleDateFormat(format).format(date);
    }

    public static Date asDate(LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
    }

    public static LocalDate asLocalDate(String date) {
        LocalDate localDate = LocalDate.parse(date);
        return localDate;
    }

    public static LocalDate asLocalDateWithFormat(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        try {
            LocalDate localDate = LocalDate.parse(date, formatter);
            return localDate;
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format: " + e.getMessage());
            return null; // Or handle the exception as needed
        }
    }


    public static Date asDate(String date) {
        return java.sql.Date.valueOf(date);
    }

    public static Date asDateTime(String stringToDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // formatter.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        //String dateInString = "22-01-2015 10:15:55 AM";
        Date date = formatter.parse(stringToDate);
        String formattedDateString = formatter.format(date);
        //System.out.println(LocalDateTime.parse(date));
        return date;
    }

    public static Date getDateFromString(String string) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
        Date date;
        try {
            date = formatter.parse(string);
        } catch (ParseException e) {
            throw new RuntimeException("Please provide correct date formatter");
        }
        return date;
    }

    public static int getCurrentYear() {
        LocalDate current_date = LocalDate.now();
        int current_Year = current_date.getYear();
        return current_Year;
    }

    public static String getDateFromDateTime(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD);
        return formatter.format(date);
    }

    public static Integer getYearFromDate(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.getYear();
    }

    public static Date getDateAsStartOfDayFromDate(Date date) {
        if (Objects.isNull(date)) return null;
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Period dateDiffAsPeriod(LocalDate from, LocalDate to){
       return Period.between(from, to);
    }

    public static boolean isBefore(LocalDate specifiedDate){
        System.out.println("is before" + specifiedDate.isBefore(LocalDate.now()));
        return specifiedDate.isBefore(LocalDate.now());
    }

}
