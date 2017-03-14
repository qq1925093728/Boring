package com.susion.boring.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 9/2/16
 * Time: 6:07 PM
 * Desc: TimeUtils
 */
public class TimeUtils {

    /**
     * Parse the time in milliseconds into String with the format: hh:mm:ss or mm:ss
     *
     * @param duration The time needs to be parsed.
     */
    @SuppressLint("DefaultLocale")
    public static String formatDuration(int duration) {
        duration /= 1000; // milliseconds into seconds
        int minute = duration / 60;
        int hour = minute / 60;
        minute %= 60;
        int second = duration % 60;
        if (hour != 0)
            return String.format("%2d:%02d:%02d", hour, minute, second);
        else
            return String.format("%02d:%02d", minute, second);
    }

    public static String getDurationString(long durationMs, boolean negativePrefix) {
        return String.format(Locale.getDefault(), "%s%02d:%02d",
                negativePrefix ? "- " : "",
                TimeUnit.MILLISECONDS.toMinutes(durationMs),
                TimeUnit.MILLISECONDS.toSeconds(durationMs) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(durationMs))
        );
    }


    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return  format.format(date);
    }

    public static String getDateCnDescForZhiHu(Date date) {
        String dateDesc;
        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        int month = calender.get(Calendar.MONTH)+1;
        int day = calender.get(Calendar.DATE);
        int weekDay = calender.get(Calendar.DAY_OF_WEEK) - 1;
        dateDesc = month+"月"+day+"日 "+"星期"+ translateToCn(weekDay);
        return dateDesc;
    }

    private static String translateToCn(int weekDay) {

        switch (weekDay) {
            case 0:
                return "一";
            case 2:
                return "二";
            case 3:
                return "三";
            case 4:
                return "四";
            case 5:
                return "五";
            case 6:
                return "六";
            case 7:
                return "日";
        }
        return "八";
    }

    public static Date getDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat();
        try {
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
