package com.java.juc.executor;

import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecuteTest {

	public static void synchronousQueue(){
		ThreadPoolExecutor aPool = new ThreadPoolExecutor(10,20,5,TimeUnit.MINUTES,new SynchronousQueue<Runnable>());
		for(int i=0;i<12;i++){
			aPool.execute(new R(i));
		}
	}
	static class R implements Runnable{
       int i;
       public R(int i){
    	   this.i=i;
       }
		@Override
		public void run() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()+"===="+i);
		}
	
	}
	public static void linkedBlockingQueue(){
		ThreadPoolExecutor aPool = new ThreadPoolExecutor(10,20,5,TimeUnit.MINUTES,new LinkedBlockingQueue<Runnable>());
		for(int i=0;i<22;i++){
			aPool.execute(new R(i));
		}
	}
	public static void arrayBlockingQueue(){
		ThreadPoolExecutor aPool = new ThreadPoolExecutor(10,20,5,TimeUnit.MINUTES,new ArrayBlockingQueue<Runnable>(10));
		for(int i=0;i<12;i++){
			aPool.execute(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName()+"==="+new Date());
				}
			});
		}
	}
	public static void main(String[] args) {
		//synchronousQueue();
		arrayBlockingQueue();
		//linkedBlockingQueue();
	}
}
