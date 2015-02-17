package com.disruptor.future;

public interface EventFuture {

	void addListener(EventFutureListener listener);
	
	boolean setSuccess();
}
