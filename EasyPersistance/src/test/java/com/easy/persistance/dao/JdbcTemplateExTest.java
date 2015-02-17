package com.easy.persistance.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.easy.persistance.BaseTest;


public class JdbcTemplateExTest extends BaseTest {

	@Resource
	private IJdbcTemplateEx jdbcTemplateEx; 
	
	@Test
	public void testBatchInsert(){
		String sql = "insert into course (cou_name,cou_no) values(?,?)";
		List<List<Object>> valuesList = new ArrayList<List<Object>>();
		List<Object> l1 = new ArrayList<Object>();
		l1.add("a");
		l1.add("no1");
		valuesList.add(l1);
		
		List<Object> l2 = new ArrayList<Object>();
		l2.add("a2");
		l2.add("no2");
		valuesList.add(l2);
		int[] i=jdbcTemplateEx.batchInsert(sql, valuesList);
		System.out.println(i);
	}
	
	
}
