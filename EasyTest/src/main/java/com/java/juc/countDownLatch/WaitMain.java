package com.java.juc.countDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 *
 */
public class WaitMain 
{
    public static void main( String[] args ) throws InterruptedException
    {
    	int size = 100;
    	ExecutorService threadPool = Executors.newFixedThreadPool(size);
    	CountDownLatch start = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(size);
        for(int i=0;i<size;i++){
        	threadPool.execute(new Handler(start,end));
        }
        System.out.println("start");
        start.countDown();
        end.await();
        System.out.println("end");
        threadPool.shutdown();
    }
    static class Handler implements Runnable{
        private CountDownLatch start;
        private CountDownLatch end;
        public Handler(CountDownLatch start,CountDownLatch end){
        	this.start = start;
        	this.end = end;
        }
		@Override
		public void run() {
			try {
				start.await();
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			try {
				Thread.sleep(Math.round(Math.random()*2000+1000));
				System.out.println(Thread.currentThread().getName()+"is finished");
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			end.countDown();
		}
    	
    }
}
