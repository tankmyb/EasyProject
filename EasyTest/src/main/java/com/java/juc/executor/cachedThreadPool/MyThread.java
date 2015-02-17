package com.java.juc.executor.cachedThreadPool;

import java.util.concurrent.CountDownLatch;

public class MyThread implements Runnable {
    private int count = 1, number;
    private CountDownLatch begin;
    private CountDownLatch end;
    public MyThread(CountDownLatch begin,CountDownLatch end,int num) {
       number = num;
       this.begin = begin;
       this.end =end;
       System.out.println("Create Thread-" + number);
    }
 
    public void run() {
    	try {
			begin.await();// 必须等到裁判countdown到0的时候才开始
			Thread.sleep((long) (Math.random() * 100));// 模拟跑步需要的时间
			System.out.println("Thread-" + number + " run " + count+" time(s)，thread name:"+Thread.currentThread().getName());

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			end.countDown();// 向评委报告跑到终点了
		}
       
    }


}
