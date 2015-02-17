package com.java.juc.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 测试callable超时是否不会释放线程
 * @author Administrator
 *
 */
class MyCallable implements Callable<String>{
	private String returnMsg = null;
	@Override
	public String call() throws Exception {
		while (true) {
			if (returnMsg != null) {
				return returnMsg;
				
			} else{
				try {
						wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
public class TestMemeryLeak{

	public static void main(String[] args)  throws Exception{
		Thread.sleep(20000L);
		System.out.println("==========start");
	    ExecutorService cachedService =Executors.newCachedThreadPool();
	    int i=0;
	    while(true){
	    	if(i<300){
	    		Future<String> task = cachedService.submit(new MyCallable());
		    	try {
		    		task.get(2000,TimeUnit.MILLISECONDS);
				} catch (Exception e) {
					e.printStackTrace();
				}
	    	}else {
	    		Thread.sleep(100L);
	    	}
	    	
	    	
	    }
	    //cachedService.shutdown();
	}
	
}
