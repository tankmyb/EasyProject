package com.java.juc.cyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WatiMain {

	static class Handler implements Runnable{
        private CyclicBarrier barrier;
        public Handler(CyclicBarrier barrier){
        	this.barrier = barrier;
        }
		@Override
		public void run() {
			
			try {
				Thread.sleep(Math.round(Math.random()*2000+1000));
				System.out.println(Thread.currentThread().getName()+"is finished");
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			try {
				barrier.await();
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
    	
    }
	public static void main(String[] args) {
		int size=20;
		 final ExecutorService threadPool = Executors.newFixedThreadPool(size);
		 CyclicBarrier barrier = new CyclicBarrier(size,new Runnable() {
			
			@Override
			public void run() {
				System.out.println("end");
				threadPool.shutdown();
			}
		});

	    
	     for(int i=0;i<size+1;i++){
	        	threadPool.execute(new Handler(barrier));
	        }
	        System.out.println("start");
	        
	}
    
}
