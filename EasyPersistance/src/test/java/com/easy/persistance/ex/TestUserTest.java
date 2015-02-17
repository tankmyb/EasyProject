package com.easy.persistance.ex;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.easy.persistance.BaseTest;
import com.easy.persistance.jdbcexector.JdbcExector;
import com.easy.persistance.jdbcexector.TestBaseDao;
import com.easy.persistance.jdbcexector.TestUser;

public class TestUserTest extends BaseTest {

	@Resource 
	private JdbcTemplate jdbcTemplate;
	@Resource
	private TestBaseDao dao;
	@Resource
	private JdbcExector jdbcExector;
	@Test
	public void test() { 
		TestUser t = new TestUser();
		t.setCnName("aaaa人");
		dao.insert(t);
		System.out.println("========================sss");
		jdbcTemplate.execute("insert into test_user (cn_name) values('d 要人')");
	}
	@Test
	public void testFindAll(){
		List<TestUser> list =dao.findAll();
		for(TestUser t:list){
			System.out.println(t.getCnName());
		}
	}
	@Test
	public void testjdbcExector(){
		TestUser t = new TestUser();
		t.setCnName("aaaa人");
		jdbcExector.insert(t);
	}

}
