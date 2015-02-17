package com.easy.persistance.parser;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;


public class InsertParserTest {

	@Test
	public void testGetSql(){
		Map<String,Object> record = new HashMap<String, Object>();
		record.put("cou_name", "couName");
		InsertParser parser = new InsertParser("course",record);
		System.out.println(parser.getSQL());
		System.out.println(parser.getValues().get(0));
	}
}
