package com.disruptor.c1p1;

public interface CounterTracer {

	public void countDown();
	public long getMilliTimeSpan();
	public void start();
	public void await() throws InterruptedException;
}
