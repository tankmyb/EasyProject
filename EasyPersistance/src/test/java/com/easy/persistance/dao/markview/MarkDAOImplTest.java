package com.easy.persistance.dao.markview;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.easy.persistance.BaseTest;
import com.easy.persistance.orm.MarkRow;
import com.easy.persistance.orm.MarkSelect;

public class MarkDAOImplTest extends BaseTest{

	@Resource
	private MarkDAO dao;
	@Test
	public void testGet() {
		MarkRow row = dao.get(1);
	}
	@Test
	public void testUpdate() {
		MarkRow row = dao.get(1);
		row.setMaMark(89);
		dao.update(row);
	}
	@Test
	public void testDelete() {
		MarkRow row = dao.get(1);
		dao.delete(row);
	}
	@Test
	public void testDeleteArr() {
		dao.delete("3,4");
	}
	@Test
	public void testGetList(){
		MarkSelect ms = new MarkSelect();
		ms.setMaCouNo("no1 ' or 1='1");
		ms.setMaStuNo("no1");
		List<MarkRow> list = dao.getList(ms);
		System.out.println(list.size()+"==22=");
	}
	@Test
	public void testCache(){
		List<MarkRow> list = dao.getList("a");
		System.out.println(list.size());
	}
}
