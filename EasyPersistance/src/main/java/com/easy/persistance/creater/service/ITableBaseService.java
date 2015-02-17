package com.easy.persistance.creater.service;

import java.util.List;

import com.easy.persistance.sql.select.ISelect;


public interface ITableBaseService<T> {

	void insert(T row);

	List<T> getList(ISelect select);
	
	T get(Integer id);
	
	List<T> getAll();
	/**
	 * 
	 * @param row
	 * @return
	 * -2:没有更新字段
	 */
	int update(T row);
	
	int delete(T row);
	
	int delete(String ids);
	
	int count(ISelect select);
	
	int insertWithGeneratedKey(T row);
	
}
