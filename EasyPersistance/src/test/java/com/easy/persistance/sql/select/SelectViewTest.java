package com.easy.persistance.sql.select;

import org.junit.Test;

import com.easy.persistance.BaseTest;
import com.easy.persistance.orm.MarkViewSelect;


public class SelectViewTest extends BaseTest{
   
	@Test
	public void testMarkViewSql(){
		MarkViewSelect mvs = new MarkViewSelect();
		mvs.addColumns(mvs.meta.F_couId,mvs.meta.F_maMark);
		//Assert.assertEquals(mvs.getSimpleSql(),"select mv.cou_id,mv.ma_mark from mark_view mv");
	}
	
}
