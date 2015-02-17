package com.easy.persistance.creater.service;

import java.util.List;

import com.easy.persistance.sql.select.ISelect;


public interface IViewBaseService<T> {


	List<T> getList(ISelect select);
	
	
	List<T> getAll();

	int count(ISelect select);
}
