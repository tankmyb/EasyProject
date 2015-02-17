package com.easy.persistance.resultset;

import java.io.Serializable;
import java.util.Map;

public class CustomRow extends DBRow implements Serializable{

	private static final long serialVersionUID=1L;

	@SuppressWarnings("unchecked")
	public void remove(String name,String key){
		Object value=values.get(name);
		if(value==null){
			throw new NullPointerException("No mapped value for '"+name+"("+key+")'");
		} else if(value instanceof Map){
			((Map)value).remove(key);
		} else{
			throw new IllegalArgumentException("Non-mapped property for '"+name+"("+key+")'");
		}

	}

}
