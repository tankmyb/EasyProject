package com.java.juc.executor.cachedThreadPool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPool {
	public static void main(String[] args) {
		CountDownLatch begin = new CountDownLatch(1);
		CountDownLatch end = new CountDownLatch(50);
	       ExecutorService exec = Executors.newCachedThreadPool();
	       for (int i = 0; i < 50; i++){
	           exec.execute(new MyThread(begin,end,i));
	       }
	       System.out.println("比赛开始");
			begin.countDown();// 宣布开始
			try {
				end.await();// 等待结束
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				System.out.println("比赛结束");
			}
	       exec.shutdown();
	    }

}
