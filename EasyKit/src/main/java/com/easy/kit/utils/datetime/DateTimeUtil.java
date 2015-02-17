package com.easy.kit.utils.datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateTimeUtil {

	/**
	 * yyyyMMdd
	 */
	public final static String COMPACTDATE_FORMAT = "yyyyMMdd";
	/**
	 * yyyyMMddHHmmss
	 */
	public final static String COMPACTDATETIME_FORMAT = "yyyyMMddHHmmss";
	/**
	 * yyyy-MM-dd
	 */
	public final static String DATE_FORMAT = "yyyy-MM-dd";
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public final static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/**
	 * yyyy-MM-dd HH:mm:ss.SSS
	 */
	public final static String FULL_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

	/**
	 * 将字符串按照自定义格式转换成java.util.Date类型<br>
	 * 
	 * @param dataStr
	 * @param formatStr
	 * @return
	 */
	public static java.util.Date parseUtilDate(String dateStr, String formatStr) {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		java.util.Date utilDate;
		try {
			utilDate = format.parse(dateStr);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return utilDate;
	}

	/**
	 * 将java.util.Date类型转换成 自定义格式 字符串<br>
	 */
	public static String formatUtilDate(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	

	/**
	 * 先将字符串按照自定义格式转换成 java.sql.Date类型<br>
	 */
	public static java.sql.Date parseSQLDate(String dataStr, String pattern) {
		java.util.Date utilDate = parseUtilDate(dataStr, pattern);
		return new java.sql.Date(utilDate.getTime());
	}

	/**
	 * 先将字符串按照自定义格式转换成java.sql.Time类型<br>
	 * 
	 * @param dataStr
	 *            时间字符串
	 * @param pattern
	 *            格式化（如：HH:mm:dd）
	 * @return java.sql.Time
	 */
	public static java.sql.Time parseSQLTime(String dataStr, String pattern) {
		java.util.Date utilDate = parseUtilDate(dataStr, pattern);
		return new java.sql.Time(utilDate.getTime());
	}

	/**
	 * 将字符串按照自定义格式转换java.sql.Timestamp类型<br>
	 * 
	 * @param dataStr
	 *            日期字符串
	 * @param pattern
	 *            格式(如：yyyy-MM-dd HH:mm:dd)
	 * @return java.sql.Timestamp
	 */
	public static java.sql.Timestamp parseSQLTimestamp(String dataStr) {
		java.util.Date utilDate = parseUtilDate(dataStr, DATETIME_FORMAT);
		return new java.sql.Timestamp(utilDate.getTime());
	}

	/**
	 * 将字符串按照 yyyy-MM-dd 格式 转换成java.util.Date类型<br>
	 */
	public static Date parseUtilDate(String s) {
		return parseUtilDate(s, DATE_FORMAT);
	}

	/**
	 * 将字符串转换成 HH:mm:ss 格式的java.util.Date类型<br>
	 */
	static public Date parseUtilTime(String s) {
		return parseUtilDate(s, "HH:mm:ss");
	}

	/**
	 * 将字符串转换成 yyyy-MM-dd HH:mm:ss 格式的java.util.Date类型<br>
	 */
	static public Date parseUtilDateTime(String s) {
		return parseUtilDate(s, DATETIME_FORMAT);
	}

	/**
	 * 将java.util.Date类型转换成 yyyy-MM-dd 字符串格式<br>
	 */
	static public String getDateStr(Date date) {
		return formatUtilDate(date, DATE_FORMAT);
	}

	/**
	 * 将java.util.Date类型转换成 hh:mm:ss 字符串格式<br>
	 */
	static public String getTimeStr(Date date) {
		return formatUtilDate(date, "HH:mm:ss");
	}

	/**
	 * 将java.util.Date类型转换成 yyyy-MM-dd HH:mm:ss 字符串格式<br>
	 */
	static public String getDateTimeStr(Date date) {
		return formatUtilDate(date, DATETIME_FORMAT);
	}

	/**
	 * 取得Date型中的年份<br>
	 */
	public static int yearOfDate(Date date) {
		return CalendarUtil.getCalendar(date).get(Calendar.YEAR);
	}

	/**
	 * 取得Date型中的月份<br>
	 */
	static public int monthOfDate(Date date) {
		return CalendarUtil.getCalendar(date).get(Calendar.MONTH);
	}

	/**
	 * 取得Date型中的日<br>
	 */
	static public int dayOfDate(Date date) {
		return CalendarUtil.getCalendar(date).get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 当天时间,格式化为yyyy-MM-dd HH:mm:ss<br>
	 */
	static public String getTodayDateTime() {
		return getDateTimeStr(new Date());
	}

	/*
	 * 加減當前日期 +為加， -為減
	 */
	static public Date addDate(Date date, int iYear, int iMonth, int iDate) {
		GregorianCalendar newDate = CalendarUtil.getCalendar(date);
		newDate.add(GregorianCalendar.YEAR, iYear);
		newDate.add(GregorianCalendar.MONTH, iMonth);
		newDate.add(GregorianCalendar.DATE, iDate);
		return newDate.getTime();
	}

	/*
	 * 加減當前时间 +為加， -為減
	 */
	public static Date addTime(Date date, int hour, int minute, int second) {
		GregorianCalendar newDate = CalendarUtil.getCalendar(date);
		newDate.add(GregorianCalendar.HOUR, hour);
		newDate.add(GregorianCalendar.MINUTE, minute);
		newDate.add(GregorianCalendar.SECOND, second);
		return newDate.getTime();
	}

	/**
	 * 将日期的时分秒清零
	 * 
	 * @param date
	 * @return
	 */
	public static Date adjustStartDate(Date date) {
		if (date == null) {
			return null;
		}
		GregorianCalendar gc = CalendarUtil.getCalendar(date);
		gc.set(GregorianCalendar.HOUR_OF_DAY, 0);
		gc.set(GregorianCalendar.MINUTE, 0);
		gc.set(GregorianCalendar.SECOND, 0);
		return gc.getTime();
	}

	/**
	 * 将日期的时分秒调为23时59分59秒
	 * 
	 * @param date
	 * @return
	 */
	public static Date adjustEndDate(Date date) {
		if (date == null) {
			return null;
		}
		GregorianCalendar gc = CalendarUtil.getCalendar(date);
		gc.set(GregorianCalendar.HOUR_OF_DAY, 23);
		gc.set(GregorianCalendar.MINUTE, 59);
		gc.set(GregorianCalendar.SECOND, 59);
		return gc.getTime();
	}

	public static String formatElapse(long ms) {
		long elapse = ms;
		String result = null;
		int SSS = (int) (elapse % 1000);
		result = SSS + "毫秒";
		if (SSS < elapse) {
			elapse = elapse / 1000;
			int ss = (int) (elapse % 60);
			result = ss + "秒" + result;
			if (ss < elapse) {
				elapse = elapse / 60;
				int mm = (int) (elapse % 60);
				result = mm + "分" + result;
				if (mm < elapse) {
					elapse = elapse / 60;
					int HH = (int) (elapse % 60);
					result = HH + "小時" + result;
					if (HH < elapse) {
						elapse = elapse / 24;
						int dd = (int) (elapse % 24);
						result = dd + "天" + result;
					}
				}
			}
		}
		return result;
	}
	/**
	 * 计算日期在本周里的开始日期
	 * @param date
	 * @return
	 */
	public static Calendar getBeginCalendarOfWeek(Date date) {
		Calendar cal = CalendarUtil.getCalendar(date);
		int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 2;
		cal.add(Calendar.DATE, -day_of_week);
		Calendar calendarBeginTime = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal
				.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		return calendarBeginTime;
	}
	/**
	 * 计算日期在本周里的最后日期
	 * @param date
	 * @return
	 */
	public static Calendar getEndCalendarOfWeek(Date date) {
		Calendar cal = CalendarUtil.getCalendar(date);
		int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 2;
		cal.add(Calendar.DATE, 6-day_of_week);
		Calendar calendarEndTime = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal
				.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
		return calendarEndTime;
	}
	/**
	 * 计算日期在本月里的开始日期
	 * @param date
	 * @return
	 */
	public static Calendar getBeginCalendarOfMonth(Date date) {
		Calendar cal = CalendarUtil.getCalendar(date);
		Calendar calendarBeginTime = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal
				.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		calendarBeginTime.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return calendarBeginTime;
	}
	/**
	 * 计算日期在本月里的最后日期
	 * @param date
	 * @return
	 */
	public static Calendar getEndCalendarOfMonth(Date date) {
		Calendar cal = CalendarUtil.getCalendar(date);
		Calendar calendarEndTime = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal
				.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
		calendarEndTime.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendarEndTime;
	}
	/**
	 * 相隔毫秒数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long getApartMilliTime(Date startDate,Date endDate){
		return endDate.getTime() - startDate.getTime();
	}
	/**
	 * 相隔天数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getApartDay(Date startDate,Date endDate){
		long day = getApartMilliTime(startDate,endDate) / (24 * 60 * 60 * 1000);
		return (int) day;
	}
	/**
	 * 相隔月数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getApartMonth(Date startDate,Date endDate){
		Calendar startCal = CalendarUtil.getCalendar(startDate);
		Calendar endCal = CalendarUtil.getCalendar(endDate);
		return (endCal.get(Calendar.YEAR) - startCal.get(Calendar.YEAR)) * 12 + (endCal.get(Calendar.MONTH) - startCal.get(Calendar.MONTH));
	}
	/**
	 * 相隔月数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getApartWeek(Date startDate,Date endDate){
		int weeks=0;
		Calendar startCal = CalendarUtil.getCalendar(startDate);
		Calendar endCal = CalendarUtil.getCalendar(endDate);
		while (startCal.before(endCal)) {
			// 如果开始日期和结束日期在同年、同月且当前月的同一周时结束循环  
			if (startCal.get(Calendar.YEAR) == endCal.get(Calendar.YEAR)
					&& startCal.get(Calendar.MONTH) == endCal.get(Calendar.MONTH)
					&& startCal.get(Calendar.DAY_OF_WEEK_IN_MONTH) == endCal.get(
							Calendar.DAY_OF_WEEK_IN_MONTH)) {
				break;

			}
			startCal.add(Calendar.DAY_OF_YEAR, 7);
			weeks += 1;

		}
		if (startCal.getTimeInMillis() != endCal.getTimeInMillis()) {
			weeks++;
		}
		return weeks;
	}
}
