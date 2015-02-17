package com.java.singleAsync;

public interface EventEmitter {

	public void on(String eventName,EventHandler handler);
	
	
	public void emit(String eventName,Object... args);
	
	public void remove(String eventName);
}
