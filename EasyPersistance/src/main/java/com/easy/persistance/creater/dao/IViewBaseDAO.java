package com.easy.persistance.creater.dao;

import java.util.List;

import com.easy.persistance.page.ext.Page;
import com.easy.persistance.page.ext.PageQuery;
import com.easy.persistance.sql.select.ASelect;
import com.easy.persistance.sql.select.ISelect;


public interface IViewBaseDAO<T> {


	List<T> getList(ISelect select);
	
	T get(ISelect select);
	
	List<T> getAll();

	int count(ISelect select);
	Page getExtPage(ASelect select,PageQuery pageQuery);
}
