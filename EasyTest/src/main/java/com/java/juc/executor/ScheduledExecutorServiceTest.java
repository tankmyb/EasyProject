package com.java.juc.executor;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceTest {

	public static void main(String[] args) {
		int reconnectInterval=3000;
		ScheduledExecutorService reconnectTimer = Executors.newScheduledThreadPool(1);
		/*reconnectTimer.scheduleAtFixedRate(new Runnable() {
			public void run() {
				System.out.println("=================1"+new Date());
			}
		},reconnectInterval, reconnectInterval, TimeUnit.MILLISECONDS);*/
		reconnectTimer.schedule(new Runnable() {
			public void run() {
				System.out.println("=================1"+new Date());
			}
		},reconnectInterval, TimeUnit.MILLISECONDS);
	}
}
