package com.guava.callback.v1;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Test {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		// 除了ListenableFuture,guava还提供了FutureCallback接口,相对来说更加方便一些.
		int size=100000;
		CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch end = new CountDownLatch(size);
		ExecutorService es = Executors.newCachedThreadPool();
		for(int i=0;i<size;i++){
		Future<String> submit = es.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				//System.out.println(new Date()+"开始工作"+Thread.currentThread().getName());
				//Thread.sleep(5000L);
				//System.out.println("结束工作"+Thread.currentThread().getName());
				return Thread.currentThread().getName();
			}
			
		});
		Futures.addFuture(submit, new Callback<String>() {

			@Override
			public void onSuccess(String value) {
				//System.out.println(value+"========"+new Date());
				end.countDown();
			}
		}, es);
		}
		 System.out.println("start");
	        start.countDown();
	        try {
				end.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        System.out.println("end:"+(System.currentTimeMillis()-startTime));
	        es.shutdown();
	}
}
