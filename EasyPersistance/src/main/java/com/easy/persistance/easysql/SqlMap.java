package com.easy.persistance.easysql;

import java.util.HashMap;

public class SqlMap extends HashMap<String, String>{

	private static final long serialVersionUID = 1L;

	public SqlMap(){
		super();
	}
	@Override
	public String put(String key, String value) {
		if(this.containsKey(key)){
			throw new RuntimeException(key+"已重复，请检查");
		}
		return super.put(key, value);
	}

}
