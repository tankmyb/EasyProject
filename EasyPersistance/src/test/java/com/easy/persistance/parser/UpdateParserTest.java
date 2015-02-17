package com.easy.persistance.parser;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.easy.persistance.orm.CourseRow;


public class UpdateParserTest {

	@Test
	public void testGetSql(){
		Map<String,Object> record = new HashMap<String, Object>();
		record.put("cou_name", "couName");
		record.put("id", 1);
		UpdateParser parser = new UpdateParser("course","id",1,record);
		System.out.println(parser.getSQL());
		System.out.println(parser.getValues().get(0));
	}
}
