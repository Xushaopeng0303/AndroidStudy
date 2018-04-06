package com.xsp.library.util.time;

import android.text.TextUtils;

import com.xsp.library.util.BaseUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Get time related information
 */
public class TimeUtil extends BaseUtil {

    /**
     * format current time, if format is empty or null, return empty
     *
     * @param format "yyyy-MM-dd HH:mm:ss" .etc
     */
    public static String formatTime(String format) {
        if (TextUtils.isEmpty(format)) {
            return "";
        }

        return formatTime(format, new Date());
    }

    /**
     * format date, if format is empty or null, return empty
     *
     * @param format "yyyy-MM-dd HH:mm:ss" .etc
     * @param date date
     */
    public static String formatTime(String format, Date date) {
        if (TextUtils.isEmpty(format) || date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(date);
    }

    /**
     * format a certificate to two digits，For example, input 6 output 06
     */
    public static String formatToTwoDigits(int date) {
        return new java.text.DecimalFormat("00").format(date);
    }

    /**
     * get the current time for the first few weeks of the month
     */
    public static int getWeekOfMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * get the current time for the first few days of the week
     */
    public static int getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * get the year of current time
     */
    public static int getYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * get the month of current time
     */
    public static int getMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH);
    }

    /**
     * get the day of current time
     */
    public static int getDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DATE);
    }

    /**
     * get the first seconds of this morning's time stamp
     */
    public static long getDayBegin() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 001);
        return calendar.getTimeInMillis();
    }

    /**
     * get a year of a certain number of days
     *
     * @throws Exception
     */
    public static int getDaysNumOfMonth(int year, int month) throws Exception {
        String data = year + "-" + formatToTwoDigits(month);
        DateFormat dateFormat = new SimpleDateFormat("yy-MM", Locale.getDefault());
        Date date1 = dateFormat.parse(data);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * compare the size of two times
     *
     * @param date1 date1
     * @param date2 date2
     * @param format time format like "yyyy-MM-dd HH:mm:ss" .etc of date1 and date2
     * @return 1:date1 is bigger than date2；-1:date1 is smaller than date2；0:date1 equals to date2
     */
    public static int compareDate(String date1, String date2, String format) {
        if (TextUtils.isEmpty(format)) {
            return 0;
        }

        DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

}
