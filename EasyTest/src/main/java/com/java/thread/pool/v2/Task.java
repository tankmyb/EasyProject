package com.java.thread.pool.v2;

public class Task implements Runnable { 
	@Override 
	public void run() 
	{ 
		
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	                //处理一些耗时，影响系统性能的业务逻辑放这里 
	                //这里只简单输出 
	System.out.println(Thread.currentThread().getName()+"............."); 

	} 



}
