package com.easy.persistance.parser;

import org.junit.Test;

import com.easy.persistance.orm.CourseRow;


public class BatchInsertParserTest {

	@Test
	public void testGetSql(){
		BatchInsertParser parser = new BatchInsertParser();
		CourseRow row1 = new CourseRow();
		row1.setCouName("couName");
		row1.setId(1);
		parser.addBatch(row1);
		CourseRow row2 = new CourseRow();
		row2.setCouName("couName2");
		row2.setId(2);
		parser.addBatch(row2);
		System.out.println(parser.getSQL());
		System.out.println(parser.getValues());
	}
}
