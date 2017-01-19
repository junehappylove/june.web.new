package com.june.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 将日期对象转换成字符串
	 * 
	 * @param date
	 *            日期对象
	 * @return 格式化的日期字符串
	 */
	public static String convert2String(Date date) {

		return format.format(date);
	}

	/**
	 * 将日期格式的字符串转换成日期
	 * 
	 * @param source
	 *            日期字符串
	 * @return 日期对象
	 */
	public static Date convert2Date(String source) {
		try {
			return format.parse(source);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * @return 返回系统当前时间
	 */
	public static Date getCurrentDate() {

		return Calendar.getInstance().getTime();

	}

	/**
	 * 根据格式获取当前时间
	 * 
	 * @param format
	 *            格式
	 * @return 时间字符串
	 */
	public static String getTimeOfNow(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
		return sdf.format(new Date());
	}

	/**
	 * 根据格式获取本周一的开始时间
	 * 
	 * @param format
	 *            格式
	 * @return 时间字符串
	 */
	public static String getFirstDayOfWeek(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setTimeInMillis(System.currentTimeMillis());
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return sdf.format(cal.getTime());
	}

	/**
	 * 根据格式获取本周末的结束时间
	 * 
	 * @param format
	 *            格式
	 * @return 时间字符串
	 */
	public static String getLastDayOfWeek(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setTimeInMillis(System.currentTimeMillis());
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return sdf.format(cal.getTime());
	}

	/**
	 * 时间格式的转换
	 * 
	 * @param dateStr
	 *            时间字符串
	 * @param oldFormat
	 *            格式
	 * @param newFormat
	 *            格式
	 * @return 时间字符串
	 * @throws ParseException
	 *             日期转换异常
	 */
	public static String reverseDateFormat(String dateStr, String oldFormat, String newFormat) throws ParseException {
		Date date = new SimpleDateFormat(oldFormat, Locale.CHINA).parse(dateStr);
		return new SimpleDateFormat(newFormat, Locale.CHINA).format(date);
	}

	/**
	 * 将当天时间加上或减去若干天 按格式返回
	 * 
	 * @param format
	 *            格式
	 * @param num
	 *            天数
	 * @return 日期字符串
	 */
	public static String getAfterDay(String format, int num) {
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.add(Calendar.DAY_OF_WEEK, num);
		return sdf.format(cal.getTime());
	}

	/**
	 * 将特定时间加上或减去若干天 按格式返回
	 * 
	 * @param format
	 *            格式
	 * @param num
	 *            天数
	 * @return 日期字符串
	 */
	public static String getAfterSpecificDay(String timeStr, String format, int num) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		Date date = null;
		try {
			date = sdf.parse(timeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_WEEK, num);
		return sdf.format(cal.getTime());
	}

	/**
	 * 将当天时间加上或减去若干月 按格式返回
	 * 
	 * @param format
	 *            格式
	 * @param num
	 *            天数
	 * @return 日期字符串
	 */
	public static String getAfterMonth(String format, int num) {
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.add(Calendar.MONTH, num);
		return sdf.format(cal.getTime());
	}

	/**
	 * 将当天时间加上或减去若干年 按格式返回
	 * 
	 * @param format
	 *            格式
	 * @param num
	 *            天数
	 * @return 日期字符串
	 */
	public static String getAfterYear(String format, int num) {
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.add(Calendar.YEAR, num);
		return sdf.format(cal.getTime());
	}

	/**
	 * 比较2个日期字符串的大小
	 * 
	 * @param dateStr1
	 *            日期字符串1
	 * @param dateStr2
	 *            日期字符串2
	 * @param format1
	 *            格式1
	 * @param format2
	 *            格式2
	 * @return 1：dateStr1>dateStr2 0: dateStr1=dateStr2 -1: dateStr1<dateStr2
	 */
	public static int dateStringCompare(String dateStr1, String dateStr2, String format1, String format2) {
		SimpleDateFormat sdf1 = new SimpleDateFormat(format1, Locale.CHINA);
		SimpleDateFormat sdf2 = new SimpleDateFormat(format2, Locale.CHINA);
		Calendar cal1 = Calendar.getInstance(Locale.CHINA);
		Calendar cal2 = Calendar.getInstance(Locale.CHINA);
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = sdf1.parse(dateStr1);
			date2 = sdf2.parse(dateStr2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal1.setTime(date1);
		cal2.setTime(date2);
		return cal1.compareTo(cal2);
	}

	/**
	 * 计算2个日期的间隔天数
	 * 
	 * @param t1
	 *            日期字符串1
	 * @param t2
	 *            日期字符串2
	 * @return 天数
	 * @throws ParseException
	 *             转换异常
	 */
	public static int getBetweenDays(String t1, String t2, String format1, String format2) {
		SimpleDateFormat sdf1 = new SimpleDateFormat(format1);
		SimpleDateFormat sdf2 = new SimpleDateFormat(format2);
		int betweenDays = 0;
		Date d1 = null;
		Date d2 = null;
		try {
			d1 = sdf1.parse(t1);
			d2 = sdf2.parse(t2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		// 保证第二个时间一定大于第一个时间
		if (c1.after(c2)) {
			c1.setTime(d2);
			c2.setTime(d1);
		}
		int betweenYears = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		betweenDays = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
		for (int i = 0; i < betweenYears; i++) {
			c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1));
			betweenDays += c1.getMaximum(Calendar.DAY_OF_YEAR);
		}
		return betweenDays + 1;
	}

	/**
	 * 获取指定月份的天数
	 * 
	 * @param yearMonth
	 *            年月
	 * @param format
	 *            格式
	 * @return 天数
	 */
	public static int getDaysOfMonth(String yearMonth, String format) {
		int dayNum = 0;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(yearMonth);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		dayNum = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		return dayNum;
	}
}
