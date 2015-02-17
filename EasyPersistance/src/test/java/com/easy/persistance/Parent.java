package com.easy.persistance;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"updateRecord", "record"})  
public abstract class Parent {

	private Map<String, Object> updateRecord = new HashMap<String, Object>();
	private Map<String, Object> record = new HashMap<String, Object>();
	public Map<String, Object> getRecord() {
		return record;
	}
	public Map<String, Object> getUpdateRecord() {
		return updateRecord;
	}
	public String getpName(String aa){
		return "aaa";
	}
}
