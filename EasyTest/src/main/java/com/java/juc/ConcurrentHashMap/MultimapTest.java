package com.java.juc.ConcurrentHashMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class MultimapTest {

	private static Logger logger = LoggerFactory.getLogger("order");
	static int[] i = new int[1];
	static AtomicInteger ai = new AtomicInteger();
	
	static Multimap<String, Integer> map = ArrayListMultimap.create(); 
	static Map locks = new HashMap();
	static List lockKeys = new ArrayList();
	static {

		
		for (int i=0;i<10000;i++) {
			Object lockKey = new Object();
			lockKeys.add(lockKey);
			locks.put(lockKey, new Object());
		}

	}
	public static void put(String key, Integer value) {
		Object lockKey = lockKeys.get(key.hashCode() % lockKeys.size());  
		   Object lock = locks.get(lockKey); 
		synchronized (lock) {
		map.put(key, value);
		}
		
	}

	public static int size(String key) {
		Object lockKey = lockKeys.get(key.hashCode() % lockKeys.size());  
		   Object lock = locks.get(lockKey); 
		synchronized (lock) {
		 try{
		return map.get(key).size();
		 }catch(Exception e){
		 return 0;
		 }
		}
	}

	public static  Collection<Integer> remove(String key) {
		Object lockKey = lockKeys.get(key.hashCode() % lockKeys.size());  
		   Object lock = locks.get(lockKey); 
		synchronized (lock) {
		return map.removeAll(key);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println("===========start");
		long startTime = System.currentTimeMillis();
		int size = 1000000;
		ExecutorService threadPool = Executors.newCachedThreadPool();
		CountDownLatch end = new CountDownLatch(size * 7);
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < 7; j++) {
				threadPool.execute(new T(i + "", j, end));
			}

		}
		end.await();
		System.out.println(System.currentTimeMillis() - startTime);
		System.out.println(ai.intValue());
	}

	static class T implements Runnable {

		private String key;
		private Integer value;
		CountDownLatch end;

		public T(String key, Integer value, CountDownLatch end) {
			this.key = key;
			this.value = value;
			this.end = end;
		}

		@Override
		public void run() {
			Object lockKey = lockKeys.get(key.hashCode() % lockKeys.size());  
			   Object lock = locks.get(lockKey); 
			synchronized (lock) {
				put(key, value);
				if (size(key) == 7) {
					// System.out.println(key+"===="+map.get(key));
					
					if(remove(key)!=null){
						ai.incrementAndGet();
					}
					}
				}
				end.countDown();
			}

		}


}
