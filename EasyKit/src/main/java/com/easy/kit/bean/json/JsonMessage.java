package com.easy.kit.bean.json;

import java.util.HashMap;
import java.util.Map;

public class JsonMessage {
	private static String SUCCESS_KEY="success";
	private static String MSG_KEY="msg";
	private Map<String,Object> map = new HashMap<String,Object>();

	public JsonMessage() {
		
	}
	public JsonMessage(boolean success) {
		map.put(SUCCESS_KEY, success);
	}
	public JsonMessage(boolean success,String msg) {
		map.put(SUCCESS_KEY, success);
	    map.put(MSG_KEY, msg);
	}
	public void add(String key,Object value){
		map.put(key, value);
	}

	public Map<String,Object> toMap() {
		return map;
	}
	public String getMsg(){
		return (String)map.get(MSG_KEY);
	}
	
	public boolean uccess() {
		return (Boolean)map.get(SUCCESS_KEY);
	}
	public void setMsg(String msg){
		map.put(MSG_KEY, msg);
	}
	
	public void isSuccess(boolean isSuccess) {
		 map.put(SUCCESS_KEY,isSuccess);
	}
}
