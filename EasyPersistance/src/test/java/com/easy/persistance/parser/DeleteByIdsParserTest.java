package com.easy.persistance.parser;

import org.junit.Test;


public class DeleteByIdsParserTest {

	@Test
	public void testGetSql(){
		DeleteByIdsParser parser = new DeleteByIdsParser("course","id","1");
		System.out.println(parser.getSQL());
		
	}
}
