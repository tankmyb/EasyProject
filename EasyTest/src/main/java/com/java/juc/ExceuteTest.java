package com.java.juc;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExceuteTest {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService cachedService =Executors.newCachedThreadPool();
		
		final Future<String> task = cachedService.submit(new Callable<String>() {

			@Override
			public String call() throws Exception {
				Thread.sleep(5000);
				return Thread.currentThread().getName();
			}
		});
		cachedService.execute(new Runnable() {
			
			@Override
			public void run() {
				try {
					System.out.println(Thread.currentThread().getName()+"===============");
					System.out.println(task.get());
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		System.out.println("=============="+new Date());
	}
}
