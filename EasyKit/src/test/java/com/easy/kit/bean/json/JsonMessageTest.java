package com.easy.kit.bean.json;

import org.junit.Test;

import com.easy.kit.utils.json.JacksonUtil;


public class JsonMessageTest {

	@Test
	public void test(){
		JsonMessage js = new JsonMessage();
		js.add("aa", "bbbb");
		System.out.println(JacksonUtil.getJsonStr(js));
		System.out.println(JacksonUtil.getJsonStr(js.toMap()));
	}
}
