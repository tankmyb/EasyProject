package com.easy.persistance;

import org.junit.Test;

import com.easy.kit.utils.json.JacksonUtil;
import com.easy.kit.utils.log.ErrorLogger;
import com.easy.kit.utils.log.ExceptionLogger;
import com.easy.kit.utils.log.InfoLogger;
import com.easy.persistance.orm.CourseRow;


public class TestLog extends BaseTest{

	@Test
	public  void log() {
		ExceptionLogger.exception("a", "aaa");
		ErrorLogger.error("aaaaaaaaaaaa");
		InfoLogger.info("aaaaaaaaaaaaaaaaaaaainfo");
	}
	@Test
	public  void sqllog() {
		//sqlLogger.sql("first sql");
	}
	@Test
	public void testJackJson(){
		CourseRow row = new CourseRow();
		row.setCouName("aaa");
		System.out.println(JacksonUtil.getJsonStr(row));
	}
	@Test
	public void testJackJson1(){
		Sub sub = new Sub();
		sub.setName("aaa");
		System.out.println(JacksonUtil.getJsonStr(sub));
	}
	
}
