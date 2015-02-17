package com.java.juc.ConcurrentHashMap;

import java.util.ArrayList;
import java.util.List;


public class Obj {

	private int len;
	private List<String> list = new ArrayList<String>();
	public int getLen() {
		return len;
	}
	public void setLen(int len) {
		this.len = len;
	}
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	public void add(String value){
		list.add(value);
	}
	public boolean isFull(){
		return list.size()==len;
	}
	
}
