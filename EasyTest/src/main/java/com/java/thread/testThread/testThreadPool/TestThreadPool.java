package com.java.thread.testThread.testThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestThreadPool {
	public static void main(String args[]) throws InterruptedException {
		// only two threads
		Thread.sleep(10000L);
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int index = 0; index < 10000000; index++) {
			Runnable run = new Runnable() {
				public void run() {
					long time = (long) (Math.random() * 1000);
					System.out.println("Sleeping " + time + "ms");
					try {
						//Thread.sleep(time);
					} catch (Exception e) {
					}
				}
			};
			exec.execute(run);
		}
		// must shutdown
		exec.shutdown();
	}
}