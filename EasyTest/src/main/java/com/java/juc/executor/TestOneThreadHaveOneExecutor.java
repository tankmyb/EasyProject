package com.java.juc.executor;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestOneThreadHaveOneExecutor implements Runnable{
		private ConcurrentLinkedQueue<String> codeQueue;
		public TestOneThreadHaveOneExecutor(ConcurrentLinkedQueue<String> codeQueue){
			this.codeQueue = codeQueue;
		}
		@Override
		public void run() {
			ExecutorService cachedService =Executors.newCachedThreadPool();
			while(!codeQueue.isEmpty()){
				cachedService.execute(new Work(codeQueue.poll()));
			}
			cachedService.shutdown();
		}
	
	class Work implements Runnable{
    public String code;
    public Work(String code){
    	this.code = code;
    }
		@Override
		public void run() {
			try {
				Thread.sleep(2000L);
				System.out.println(Thread.currentThread().getName()+":"+code);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	public static void main(String[] args) throws InterruptedException {
		//Thread.sleep(10000L);
		for(int i=0;i<100;i++){
			ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();
			for(int j=0;j<1000;j++){
				queue.add("aaaa "+i+" "+j);
			}
			new Thread(new TestOneThreadHaveOneExecutor(queue)).start();
		}
	}
}
