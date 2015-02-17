package com.java.juc.ConcurrentHashMap;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class PutGetTest {

	public static ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<String,Integer>();
	static AtomicInteger ai = new AtomicInteger();
	public static void main(String[] args) throws InterruptedException {
		long startTime = System.currentTimeMillis();
		int size = 1000000;
		ExecutorService threadPool = Executors.newFixedThreadPool(100);
		CountDownLatch end = new CountDownLatch(size);
		for (int i = 0; i < size; i++) {
				threadPool.execute(new T(i + "", end));

		}
		end.await();
		System.out.println(System.currentTimeMillis() - startTime);
		System.out.println(ai.intValue());
	}
	static class T implements Runnable {

		private String key;
		CountDownLatch end;

		public T(String key, CountDownLatch end) {
			this.key = key;
			this.end = end;
		}

		@Override
		public void run() {

			map.put(key, 1);
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							if(map.remove(key)!=null){
								ai.incrementAndGet();
							}
							
						}
					}).start();
				end.countDown();
			}

		}

}
