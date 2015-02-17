package com.java.juc.ConcurrentHashMap;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class PutSizeTest {

	static ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<String, Integer>();
	static void put(int i){
		map.put("userId"+i, i);
	}
	static class Handler implements Runnable{
        private CountDownLatch end;
        private int i;
        public Handler(CountDownLatch end,int i){
        	this.end = end;
        	this.i=i;
        }
		@Override
		public void run() {
			
			put(i);
			end.countDown();
		}
    	
    }
	public static void main(String[] args) throws InterruptedException {
		Thread.sleep(10000);
		long startTime=System.currentTimeMillis();
		int size = 500000;
    	ExecutorService threadPool = Executors.newCachedThreadPool();
        CountDownLatch end = new CountDownLatch(size);
        for(int i=0;i<size;i++){
        	threadPool.execute(new Handler(end,i));
        }
        System.out.println("start");
        end.await();
        System.out.println("end======="+(System.currentTimeMillis()-startTime));
        threadPool.shutdown();
        Thread.sleep(100000);
		
	}

}
