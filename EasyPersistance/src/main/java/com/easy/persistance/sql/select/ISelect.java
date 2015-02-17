package com.easy.persistance.sql.select;

import java.util.List;


public interface ISelect {
	
	
	public String getSQL();
	String getCountSql();
	public List<Object> getValues();

}
