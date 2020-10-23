package com.umgsai.wx.backend.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author shangyidong
 * 2020/10/23 21:10
 */
public class CommonDateUtil {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String NUM_DATE_TIME = "yyyyMMddHHmmss";
    public static final String STRIKE_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String STRIKE_MINUTE_DATE = "yyyy-MM-dd HH:mm";
    public static final String STRIKE_MILL_DATE_TIME = "yyyy-MM-dd HH:mm:ss SSS";
    public static final String DOT_MILL_DATA_TIME = "yyyy-MM-dd HH:mm:ss.SSS";

    public static long currentTimeMills() {
        return System.currentTimeMillis();
    }

    public static Date now() {return new Date();}

    public static Date parseDate(String dateStr, String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.parse(dateStr);
    }

    public static Date parseDate(long timeStamp) {
        return new Date(timeStamp);
    }

    public static Date parseDate(String timeStamp) {
        return parseDate(Long.parseLong(timeStamp));
    }

    public static String formatCurrentDate(String format) {
        return formatDate(new Date(), format);
    }

    public static String formatDate(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static long getTimeMills(String date, String format) throws ParseException {
        return getTimeMills(parseDate(date, format));
    }

    public static long getTimeMills(Date date) {
        return date.getTime();
    }

    public static long getTimeDiffMills(Date date) {
        Date now = new Date();
        return now.getTime() - date.getTime();
    }

    public static long getTimeDiffMills(long timeStamp) {
        Date beforeDate = new Date();
        beforeDate.setTime(timeStamp);
        return getTimeDiffMills(beforeDate);
    }

    public static long getTimeDiffMills(Date startDate, Date endDate) {
        return endDate.getTime() - startDate.getTime();
    }

    public static long getTimeDiffSeconds(Date startDate, Date endDate) {
        return TimeUnit.MILLISECONDS.toSeconds(endDate.getTime() - startDate.getTime());
    }

    public static long getTimeDiffMinutes(Date startDate, Date endDate) {
        return TimeUnit.MILLISECONDS.toMinutes(endDate.getTime() - startDate.getTime());
    }

    public static long getTimeDiffHours(Date startDate, Date endDate) {
        return TimeUnit.MILLISECONDS.toHours(endDate.getTime() - startDate.getTime());
    }

    public static long getTimeDiffDays(Date startDate, Date endDate) {
        return TimeUnit.MILLISECONDS.toDays(endDate.getTime() - startDate.getTime());
    }

    public static long getCurrentHours() {
        return getHours(new Date());
    }

    public static long getHours(Date date) {
        return TimeUnit.MILLISECONDS.toHours(date.getTime());
    }

    public static Date addSeconds(Date date, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    public static Date addMinutes(Date date, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    public static Date addHours(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hours);
        return calendar.getTime();
    }

    public static Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    public static Date addCurrentDays(int days) {
        return addDays(new Date(), days);
    }
}
