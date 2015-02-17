package com.easy.persistance.parser;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;


public class DeleteParserTest {

	@Test
	public void testGetSql(){
		Map<String,Object> map =new HashMap<String, Object>();
		map.put("id", 1);
		map.put("name", "name");
		DeleteParser parser = new DeleteParser("table1",map);
		System.out.println(parser.getSQL());
		System.out.println(parser.getValues().get(1));
	}
}
