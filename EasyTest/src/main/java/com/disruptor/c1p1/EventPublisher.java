package com.disruptor.c1p1;

public interface EventPublisher {

	void start();
	
	void handle();
	
	void stop();
}
