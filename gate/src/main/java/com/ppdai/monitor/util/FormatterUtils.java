package com.ppdai.monitor.util;

import org.apache.commons.lang.time.DateFormatUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author haijiang
 * @Description FormatterUtils
 * @Date 2018/09/18
 **/
public class FormatterUtils {
    private static long TICKS_AT_EPOCH = 621355968000000000L;
    private static long TICKS_PER_MILLISECOND = 10000;
    private static long EIGHT_HOURS_MILLSECONDS = 8 * 3600 * 1000;
    private static long ONT_DAY_SECONDS = 24 * 3600;
    private static String UT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static Date convertFromNetTicks(Long ticks) {
        Date date = new Date((ticks - TICKS_AT_EPOCH) / TICKS_PER_MILLISECOND - EIGHT_HOURS_MILLSECONDS);
        return date;
    }

    public static long getNetTicks() {
        return getNetTicks(new Date());
    }

    public static long getNetTicks(Date date) {
        return ((date.getTime() + EIGHT_HOURS_MILLSECONDS) * TICKS_PER_MILLISECOND + TICKS_AT_EPOCH);
    }

    public static String date2UtString(Date date) {
        return DateFormatUtils.format(date, UT_DATE_FORMAT);
    }

    /**
     * 获取指定时间对应的毫秒数
     *
     * @param time "HH:mm:ss"
     * @return
     */
    public static long getTimeMillis(String time) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
            Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);
            return curDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取当前时间距离当天23:59:59的秒数
     *
     * @return long
     */
    public static long currentDayEarlyMorningTimeMillis() {
        try {
            long now = System.currentTimeMillis();
            SimpleDateFormat sdfOne = new SimpleDateFormat("yyyy-MM-dd");
            long overTime = (now - (sdfOne.parse(sdfOne.format(now)).getTime())) / 1000;
            return ONT_DAY_SECONDS - overTime;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String formatDate(Date date) {
        return DateFormatUtils.format(date, DATE_FORMAT);
    }

    public static String longToDateStr(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        return year + "-" + (month + 1) + "-" + day + " "
                + hour + ":" + minute + ":" + second;
    }

    public static void main(String[] args) {
        long netTicks = getNetTicks();
        System.out.println(currentDayEarlyMorningTimeMillis());
        System.out.println(netTicks);
        Date date = convertFromNetTicks(netTicks);
        System.out.println(date);
        String utString = date2UtString(date);
        System.out.println(utString);
    }
}
