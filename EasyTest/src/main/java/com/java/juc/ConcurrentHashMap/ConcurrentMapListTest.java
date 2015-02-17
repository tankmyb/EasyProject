package com.java.juc.ConcurrentHashMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.java.juc.ConcurrentHashMap.MapListSyncTest.T;

public class ConcurrentMapListTest {
	private static Logger logger = LoggerFactory.getLogger("order");
	static int[] i = new int[1];
	static AtomicInteger ai = new AtomicInteger();

	static ConcurrentHashMap<String, List<Integer>> map = new ConcurrentHashMap<String, List<Integer>>();
	static Map<Integer, Integer> locks = new HashMap<Integer, Integer>();
	static List<Integer> lockKeys = new ArrayList<Integer>();
	static {
		for (int i = 0; i < 10000; i++) {
			Integer lockKey = new Integer(i);
			lockKeys.add(i);
			locks.put(lockKey, new Integer(i));
		}
	}

	public static void put(String key, Integer value) {
		Integer lock = locks
				.get(lockKeys.get(key.hashCode() % lockKeys.size()));
		synchronized (lock) {
			List<Integer> list = map.get(key);
			if (null == list) {
				list = new ArrayList<Integer>();
			}
			list.add(value);
			map.put(key, list);
		}
	}

	public static int size(String key) {
		try {
			return map.get(key).size();
		} catch (Exception e) {
			return 0;
		}

	}

	public static List<Integer> remove(String key) {
		return map.remove(key);
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println("===========start");
		long startTime = System.currentTimeMillis();
		int size = 10000000;
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
			put(key, value);
			if (size(key) == 7) {
				// System.out.println(key+"===="+map.get(key));
				Integer lock = locks.get(lockKeys.get(key.hashCode()
						% lockKeys.size()));
				synchronized (lock) {
					if (remove(key) != null) {
						ai.incrementAndGet();
					}
				}
			}
			end.countDown();
		}

	}

}
