package com.qtong.afinance.core.util;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**
 * 常用日期时间操作封装
 * 默认时间格式为yyyyMMddHHmmss
 *
 */
public class DateUtil {
	
	//日期格式
	public static final String YYYY_MM = "yyyy-MM";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYY__MM__DD = "yyyy/MM/dd";
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYY__MM__DD_HH_MM_SS = "yyyy/MM/dd HH:mm:ss";
	public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	public static final String YYYY = "YYYY";
	public static final String HHMM = "HH:mm";
	public static final String YYYYMM = "yyyyMM";
	public static String YYYYMMDD = "yyyyMMdd";
	public static String YYYYMMDDHH = "yyyyMMddHH";
	public static String MMDD = "MM月dd日";
	public static String MM = "MM月";
	public static String MMHHmm = "MMHHmm";
	public static String YYYY__MM = "yyyy/MM";
	public static final String YYYY___MM = "yyyy年MM月";
	public static final String YYYY___MM2 = "yyyy年MM月";


	
	
	/**
	 * 获取时间格式："YYYYMMDDHHMMSS"
	 * @param date
	 * @return
	 */
	public static Date toDate(String date){  	

		return toDate(date, YYYYMMDDHHMMSS);
	}

	/**
	 * ※日期的字符串格式转Date对象实例
	 * 
	 * @param date
	 *            字符串格式的日期
	 * @param pattern
	 *            日期格式
	 * @return 返回Date对象实例
	 */
	public static Date toDate(String date, String pattern) {
		Date date2;

		if (isEmpty(pattern)) {
			pattern = YYYYMMDDHHMMSS;
		}
		try {
			if (date != null && !date.equals("")) {
				SimpleDateFormat sdf = new SimpleDateFormat(pattern);
				date2 = sdf.parse(date);
			} else {
				date2 = toLocalDate(new Date(), pattern);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Date();
		}
		return date2;
	}

	/**
	 * 日期的字符串格式转Date对象实例
	 * @param date
	 * @return
	 */
	public static String toStr(Date date) {
		return toStr(date, YYYYMMDDHHMMSS);
	}

	/**
	 * 日期的字符串格式转Date对象实例
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String toStr(Date date, String pattern) {
		String date2 = "";

		if (isEmpty(pattern)) {
			pattern = YYYYMMDDHHMMSS;
		}

		if (date != null && !date.equals("")) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			date2 = sdf.format(date);
		}

		return date2;
	}

	//得到系统时间
	public  static Date getSystemTime() {
		Calendar now = Calendar.getInstance();
		return now.getTime();
	}

	//得到系统时间
	public  static Date getMinTime() {
		Calendar date = Calendar.getInstance();
		date.set(1997, 1, 1);
		return date.getTime();
	}
	
	/**
	 * 获取当前时间戳
	 * @return
	 */
	public static String DateNow() {
		return toStr(getSystemTime(), YYYYMMDDHHMMSS) ;
	}
	/**
	 * 获取当前时间戳
	 * @return
	 */
	public static String DateNow(String pattern) {
		if (isEmpty(pattern)) {
			pattern = YYYYMMDDHHMMSS;
		}
		return toStr(getSystemTime(), pattern) ;
	}
	
	

	/**
	 * ※其他地区时间转成北京时间
	 * 
	 * @param date
	 *            日期实例对象
	 * @param pattern
	 *            日期格式
	 */
	public static Date toLocalDate(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		TimeZone zone = new SimpleTimeZone(28800000, "Asia/Shanghai");// +8区
		sdf.setTimeZone(zone);
		String sdate = sdf.format(date);

		SimpleDateFormat sdf2 = new SimpleDateFormat(pattern);
		try {
			return sdf2.parse(sdate);
		} catch (Exception e) {
			e.printStackTrace();
			return new Date();
		}
	}

	/**
	 * 获取年月日结构路径片段
	 * @return
	 */
	public static String getDatePath(){
		Calendar calendar = Calendar.getInstance();

		String path = "";
		int year = calendar.get(Calendar.YEAR);
		path = year+"";
		int month = calendar.get(Calendar.MONTH)+1;
		if(month<10)
			path = path+File.separator+"0"+month;
		else {
			path = year+File.separator+month;
		}
		int day = calendar.get(Calendar.DATE);
		if(day<10)
			return path+File.separator+"0"+day+File.separator;
		else {
			return path+File.separator+day+File.separator;
		}
	}

	/**
	 * 返回毫秒
	 * @param date
	 * @return
	 */
	public static long getMillis(Date date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}

	private static boolean isEmpty(String str) {
		if (str != null && str.trim().length() > 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * 获取当前时间的前一天
	 * @param date
	 * @return
	 */
	public static Date getBeforeDay(Date date){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}
	/**
	 * 获取当前时间的前一个月（前三十天）
	 * @param date
	 * @return
	 */
	public static Date getBeforeMonth(Date date){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -30);
		return calendar.getTime();
	}
	/**
	 * 获取当前时间的前一个星期
	 * @param date
	 * @return
	 */
	public static Date getBeforeWeek(Date date){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -6);
		return calendar.getTime();
	}
	
	/**
	 * 获取当前时间的相差天数的时间
	 * @param date
	 * @return
	 */
	public static Date getDifferDay(Date date,int number){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, number);
		return calendar.getTime();
	}
	
	/**
	 * 获取当前时间的后一天
	 * @param date
	 * @return
	 */
	public static Date getLaterDay(Date date){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	}
	/**
	 * 获取当前时间的后一小时
	 * @param date
	 * @return
	 */
	public static Date getLaterHour(Date date){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, 1);
		return calendar.getTime();
	}
	
	
	//获取当月第一天
	public static Date getInitMonth(Date date){
		GregorianCalendar gcLast=(GregorianCalendar) Calendar.getInstance();
		gcLast.setTime(date);
		//设置为第一天
		gcLast.set(Calendar.DAY_OF_MONTH,1);
		Date day_first=gcLast.getTime();
		return day_first;
	}
	
	
	
	 /**
     * 获取当前月份最后一天
     * 
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date getMaxMonthDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }
	
    /**
     * 获取当前月份最后一天时间23.59.59
     * @param date
     * @return
     */
    public static Date getMaxMonthDateTime(Date date) {
		// 获取Calendar  
		Calendar calendar = Calendar.getInstance();  
		// 设置时间,当前时间不用设置  
		calendar.setTime(date);  
		// 设置日期为本月最大日期  
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));  		
		Date setdate = calendar.getTime();
		SimpleDateFormat currentdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String setstr = currentdf.format(setdate).substring(0,8)+"235959";	
		try {
			setdate=currentdf.parse(setstr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return setdate;
    }
	
    /**
     * 获取当前天最后时间23.59.59
     * @param date
     * @return
     */
    public static Date getMaxDayDateTime(Date date) {
		// 获取Calendar
		Calendar calendar = Calendar.getInstance();
		// 设置时间,当前时间不用设置
		calendar.setTime(date);
		SimpleDateFormat currentdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String setstr = currentdf.format(date).substring(0,8)+"235959";	
		try {
			date=currentdf.parse(setstr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return date;
    }
    /**
     * 获取当前天时间00.00.00
     * @param date
     * @return
     */
    public static Date getMinDayDateTime(Date date) {
    	// 获取Calendar
    	Calendar calendar = Calendar.getInstance();
    	// 设置时间,当前时间不用设置
    	calendar.setTime(date);
    	SimpleDateFormat currentdf = new SimpleDateFormat("yyyyMMddHHmmss");
    	String setstr = currentdf.format(date).substring(0,8)+"000000";	
    	try {
    		date=currentdf.parse(setstr);
    	} catch (ParseException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	return date;
    }
    
    
    /**
     * 
     * 描述:获取下一个月的第一天.
     * 
     * @return
     */
    public static Date getPerFirstDayOfMonth(Date date) {
    	date=toDate(toStr(date, YYYY_MM_DD), YYYY_MM_DD);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }
    /**
     * 
     * 描述:获取当前月相差固定月份的第一天.
     * 
     * @return
     */
    public static Date getPerFirstDayOfMonth(Date date,int number) {
    	date=toDate(toStr(date, YYYY_MM_DD), YYYY_MM_DD);
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.add(Calendar.MONTH, number);
    	calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
    	return calendar.getTime();
    }
    /**
     * 
     * 描述:获取当前月相差固定月份的日期
     * 
     * @return
     */
    public static Date getPerDayOfMonth(Date date,int number) {
    	date=toDate(toStr(date, YYYY_MM_DD), YYYY_MM_DD);
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.add(Calendar.MONTH, number);
    	return calendar.getTime();
    }
    
    /** 
     * 获取两个日期相差几个月 
     * @author st
     * @date 2017-11-20 下午7:57:32 
     * @param start 
     * @param end 
     * @return 
     */ 
	public static int getMonth(Date start, Date end) {  
		SimpleDateFormat sdfYM = new SimpleDateFormat("yyyyMM");
		String aa=sdfYM.format(start);
		String bb=sdfYM.format(end);
		try {
			start=sdfYM.parse(aa);
			end=sdfYM.parse(bb);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        if (start.after(end)) {  
            Date t = start;  
            start = end;  
            end = t;  
        }  
        Calendar startCalendar = Calendar.getInstance();  
        startCalendar.setTime(start);  
        Calendar endCalendar = Calendar.getInstance();  
        endCalendar.setTime(end);  
        Calendar temp = Calendar.getInstance();  
        temp.setTime(end);  
        temp.add(Calendar.DATE, 1);  
  
        int year = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);  
        int month = endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);  
  
        if ((startCalendar.get(Calendar.DATE) == 1)&& (temp.get(Calendar.DATE) == 1)) {  
            return year * 12 + month + 1;  
        } else if ((startCalendar.get(Calendar.DATE) != 1) && (temp.get(Calendar.DATE) == 1)) {  
            return year * 12 + month;  
        } else if ((startCalendar.get(Calendar.DATE) == 1) && (temp.get(Calendar.DATE) != 1)) {  
            return year * 12 + month;  
        } else {  
            return (year * 12 + month - 1) < 0 ? 0 : (year * 12 + month);  
        }  
    }
	
	
	/**
	 * 判断start 是否是 end 的前一个月
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean isBeforeMonth(Date start,Date end) {
		
		int month = DateUtil.getMonth(start,end);
		if(month==1) {
			return true;
		}
		return false;
	}

	/**
	 * 判断月与月之间是否相差一个月
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean isBetweenOneMonth(Date start,Date end) {
		
		SimpleDateFormat sdfMM = new SimpleDateFormat("MM");
		String aa=sdfMM.format(start);
		String bb=sdfMM.format(end);
		
		
		SimpleDateFormat sdfYY = new SimpleDateFormat("yyyy");
		String aayy=sdfYY.format(start);
		String bbyy=sdfYY.format(end);
		
		int startDateyy = Integer.parseInt(aayy);
		int endDateyy = Integer.parseInt(bbyy);
		
		int startDate = Integer.parseInt(aa);
		int endDate = Integer.parseInt(bb);
		
		
		int yy = endDateyy - startDateyy;
		int month = endDate - startDate;
		
		if(month == 1 && yy !=0 ) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * 计算两个时间之间相差年数
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static int getYear(Date start,Date end) {
		SimpleDateFormat sdfYY = new SimpleDateFormat("yyyy");
		String aayy=sdfYY.format(start);
		String bbyy=sdfYY.format(end);
		
		int startDateyy = Integer.parseInt(aayy);
		int endDateyy = Integer.parseInt(bbyy);

		int yy = endDateyy - startDateyy;
		
		if(yy == 0 || yy == 1)
			return 1;
		return yy;
	}
	
	/**
	 * 获取当前时间00.00 00分00秒
	 * @param date
	 * @return
	 */
	public static Date getMinHourMinute(Date date) {
		// 获取Calendar
    	Calendar calendar = Calendar.getInstance();
    	// 设置时间,当前时间不用设置
    	calendar.setTime(date);
    	SimpleDateFormat currentdf = new SimpleDateFormat("yyyyMMddHHmmss");
    	String setstr = currentdf.format(date).substring(0,10)+"0000";	
    	try {
    		date=currentdf.parse(setstr);
    	} catch (ParseException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	return date;
	}
	
	/**
	 * 计算两个时间之间相差的小时数
	 * @param start
	 * @param end
	 * @return
	 */
	public static int getHourNumber(Date start,Date end) {
		SimpleDateFormat sdfYM = new SimpleDateFormat("yyyyMMHH");
		String aa=sdfYM.format(start);
		String bb=sdfYM.format(end);
  
        int hour =Integer.parseInt(bb)  - Integer.parseInt(aa) ;  
  
        return hour;
	}
	
	
}
