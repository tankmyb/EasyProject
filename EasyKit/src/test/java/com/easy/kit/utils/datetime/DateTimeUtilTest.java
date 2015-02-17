package com.easy.kit.utils.datetime;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;


public class DateTimeUtilTest {
    private  Date date = DateTimeUtil.parseUtilDate("2012-07-27 15:25:50", DateTimeUtil.DATETIME_FORMAT);
	@Test
	public void testParseUtilDate(){
		Date date = DateTimeUtil.parseUtilDate("2012-02-08");
		System.out.println(date);
	}
	@Test
	public void testYearOfDate(){
		Assert.assertEquals(DateTimeUtil.yearOfDate(date),2012);
	}
	@Test
	public void testFormatElapse(){
		System.out.println(DateTimeUtil.formatElapse(200000001));
	}
	@Test
	public void testGetBeginCalendarOfWeek(){
		Date d = DateTimeUtil.getBeginCalendarOfWeek(date).getTime();
		Assert.assertEquals(DateTimeUtil.getDateTimeStr(d), "2012-07-23 00:00:00");
	}
	@Test
	public void testGetEndCalendarOfWeek(){
		Date d = DateTimeUtil.getEndCalendarOfWeek(date).getTime();
		Assert.assertEquals(DateTimeUtil.getDateTimeStr(d), "2012-07-29 23:59:59");
	}
	@Test
	public void testGetApartDay(){
		Date startDate = DateTimeUtil.parseUtilDate("2012-06-08 23:02:25", "yyyy-MM-dd HH:mm:ss");
		Date endDate = DateTimeUtil.parseUtilDate("2012-06-28 23:02:25", "yyyy-MM-dd HH:mm:ss");
		System.out.println(DateTimeUtil.getApartDay(startDate, endDate));
		Assert.assertEquals(20,DateTimeUtil.getApartDay(startDate, endDate));
	}
	@Test
	public void testGetApartMonth(){
		Date startDate = DateTimeUtil.parseUtilDate("2012-03-08 23:02:25", "yyyy-MM-dd HH:mm:ss");
		Date endDate = DateTimeUtil.parseUtilDate("2012-07-08 23:02:25", "yyyy-MM-dd HH:mm:ss");
		System.out.println(DateTimeUtil.getApartMonth(startDate, endDate));
		Assert.assertEquals(4,DateTimeUtil.getApartMonth(startDate, endDate));
	}
	@Test
	public void testGetApartWeek(){
		Date startDate = DateTimeUtil.parseUtilDate("2012-06-08 23:02:25", "yyyy-MM-dd HH:mm:ss");
		Date endDate = DateTimeUtil.parseUtilDate("2012-07-29 23:02:25", "yyyy-MM-dd HH:mm:ss");
		System.out.println(DateTimeUtil.getApartWeek(startDate, endDate));
		Assert.assertEquals(9,DateTimeUtil.getApartWeek(startDate, endDate));
	}
}
