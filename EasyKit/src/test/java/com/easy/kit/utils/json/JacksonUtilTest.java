package com.easy.kit.utils.json;

import java.util.Date;

import org.junit.Test;


public class JacksonUtilTest {

	@Test
	public void test1(){
		Model m = new Model();
		m.setId(1);
		m.setDate(new Date());
		System.out.println(JacksonUtil.getJsonStr(m));
	}
	@Test
	public void test2(){
		
	}
}
