package com.java.map;

import java.util.HashMap;
import java.util.Map;

public class JsonMsg {
	private static final String MSG = "msg";
	private static final String STATE = "state";
	private static String SuccessMsg = "成功", FailMsg = "失败";
	Map<String,Object> map = new HashMap<String,Object>();
	public JsonMsg() {
		
	}
	public JsonMsg(Boolean state) {
		map.put(STATE, state);
		map.put(MSG, state?SuccessMsg:FailMsg);
	}
	public JsonMsg(Boolean state,String msg) {
		map.put(STATE, state);
		map.put(MSG, msg);
	}
	public void setFailSate(){
		map.put(STATE, false);
	}
	public void setSuccessSate(){
		map.put(STATE, true);
	}
	public void setSate(boolean state){
		map.put(STATE, state);
	}
	public Boolean getState(){
		return (Boolean)map.get(STATE);
	}
	public void setMsg(String msg){
		map.put(MSG, msg);
	}
	public String getMsg(){
		return (String)map.get(MSG);
	}
	public void setFailMsg(String msg){
		map.put(STATE, false);
		map.put(MSG, msg);
	}
	public void setSuccessMsg(String msg){
		map.put(STATE, true);
		map.put(MSG, msg);
	}
	public void put(String key,Object value){
		map.put(key, value);
	}


	public Map<String,Object> getMap(){
		return map;
	}

	
	public static void main(String[] args) {}
}
