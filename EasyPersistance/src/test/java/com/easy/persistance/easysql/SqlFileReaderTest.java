package com.easy.persistance.easysql;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.easy.persistance.BaseTest;
import com.easy.persistance.dao.IJdbcTemplateEx;
import com.easy.persistance.resultset.DBResultSet;

public class SqlFileReaderTest extends BaseTest {

	@Resource
	private IJdbcTemplateEx jdbcTemplateEx; 

	@Test
	public void testReadFile() {
		SqlFileReader.readFile("spring/test.sql");
	}
	@Test
	public void testGetSql() {
		SqlFileReader.readFile("sql/test.sql");
		String sql = SqlFileReader.getSqlMap().get("a1");
		System.out.println(sql);
		List<Object> values = new ArrayList<Object>();
		values.add(1);
		DBResultSet dbrs = jdbcTemplateEx.queryForDBResultSet(sql, values);
		System.out.println(dbrs.size());
	}

}
