package com.ppdai.monitor.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	
    private static final ThreadLocal<SimpleDateFormat> sdf = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    private static final ThreadLocal<DateFormat> fmt = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    
	public static Date dateAfterHour(int diff){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + diff);
		return calendar.getTime();
	}
	
	public static Date dateAfterMinute(int diff){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + diff);
		return calendar.getTime();
	}
	
	public static Date dateAfterSecond(int diff){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + diff);
		return calendar.getTime();
	}
	
	public static boolean isExpire(Date date){
		return System.currentTimeMillis() - date.getTime() > 0;
	}
	
    public static String convertDateToString(Date date) {
        if (date != null)
            return sdf.get().format(date);
        return null;
    }

    /**
     * need further consideration for perfection
     */
    public static Date convertStringToDate(String string) {
        try {
            return fmt.get().parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
	public static void main(String args[]){
		
		System.out.println(isExpire(new Date(System.currentTimeMillis() +10)));
	}
}
