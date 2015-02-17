package com.disruptor.future;

public class Event {

	private EventFuture future;
	private Object data;
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public EventFuture getFuture() {
		return future;
	}
	public void setFuture(EventFuture future) {
		this.future = future;
	}
	
}
