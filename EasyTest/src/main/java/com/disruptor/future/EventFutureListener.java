package com.disruptor.future;

public interface EventFutureListener {

	void operationComplete(EventFuture future) throws Exception;
}
