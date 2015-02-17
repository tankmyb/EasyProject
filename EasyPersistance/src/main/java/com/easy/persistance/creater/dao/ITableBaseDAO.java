package com.easy.persistance.creater.dao;

import java.util.List;

import com.easy.persistance.bean.SaveBean;
import com.easy.persistance.page.ext.Page;
import com.easy.persistance.page.ext.PageQuery;
import com.easy.persistance.sql.select.ASelect;
import com.easy.persistance.sql.select.ISelect;


public interface ITableBaseDAO<T> {

	void insert(T row);

	List<T> getList(ISelect select);
	
	T get(Integer id);
	
	T get(ISelect select);
	
	List<T> getAll();
	/**
	 * 
	 * @param row
	 * @return
	 * -2:没有更新字段
	 */
	int update(T row);
	
	SaveBean save(T row);
	
	int delete(T row);
	
	int delete(String ids);
	
	int count(ISelect select);
	
	int insertWithGeneratedKey(T row);
	
	Page getExtPage(ASelect select,PageQuery pageQuery);
}
