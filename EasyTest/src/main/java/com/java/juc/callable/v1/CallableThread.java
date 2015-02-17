package com.java.juc.callable.v1;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableThread implements Callable<Boolean>{
	private  ExecutorService cachedService =Executors.newFixedThreadPool(2);
	@Override
	public Boolean call() throws Exception {
		boolean flag = true;
		while(flag){
			Future<Boolean> future1 = cachedService.submit(new InnerThread("thread 1"));
			flag = future1.get();
			Future<Boolean> future2 = cachedService.submit(new InnerThread("thread 2"));
			flag = future2.get();
		}
		cachedService.shutdown();
		return flag;
	}
  class InnerThread implements Callable<Boolean>{
  	private String name;
  	public InnerThread(String name){
  		this.name = name;
  	}
  	public Boolean call() throws Exception {
  		try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
  		System.out.println("Thread name:"+name);
  		if(name.equals("thread 1")){
  			return true;
  		}else {
  			return false;
  		}
  	}
  }
  public static void main(String[] args) throws InterruptedException, ExecutionException {
  	/*FutureTask<Boolean> primeTask = new FutureTask(new CallableThread());
  	Thread t = new Thread(primeTask);t.start();
  	Thread.sleep(3000L);
  	if(primeTask.isDone()) {
  		System.out.println(primeTask.get());
  	}*/
  	ExecutorService cachedService =Executors.newCachedThreadPool();
  	Future<Boolean> future1 = cachedService.submit(new CallableThread());
  	System.out.println(future1.get());
  	cachedService.shutdown();
	}
}
