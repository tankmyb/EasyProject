package com.easy.persistance.orm;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.easy.persistance.util.MapUtil;
@JsonIgnoreProperties(value = {"record"}) 
public abstract class SuperRow implements  Serializable {
	public static final long serialVersionUID = 1L;
	private Map<String, Object> record = new HashMap<String, Object>();

	public SuperRow() {
	}
	protected void setValue(String key, Object val) {
			record.put(key, val);
		
	}
	protected Object getValue(String key) {
		return record.get(key);
	}

	
	public void load(SuperRow newRow) {
		load(newRow.getRecord());
	}
	public void load(Map<String, Object> newRecord) {
		record = newRecord;
	}
	public Map<String, Object> getRecord() {
		return record;
	}
	public Map<String, Object> getUpdateRecord(SuperRow newRow){
		return MapUtil.compareMap(record,newRow.getRecord());
	}
	
}
