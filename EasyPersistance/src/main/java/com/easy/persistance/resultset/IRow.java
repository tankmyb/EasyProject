package com.easy.persistance.resultset;

import java.util.Map;

public interface IRow {

	public <T> T get(String name);
	public String getString(String name);
	public Integer getInteger(String name);
	public void set(String name,Object value);
	public String toCsv()throws Exception;
	//public void toJTable() throws Exception;
	public Map<String,Object> getMap();
}
