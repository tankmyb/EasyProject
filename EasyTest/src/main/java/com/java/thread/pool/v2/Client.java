package com.java.thread.pool.v2;

public class Client {
	private static ThreadPoolManager threadPoolManager = ThreadPoolManager.getInstance(); 
    public static void main(String[] args){ 
    	for(int i=0;i<100;i++){
           threadPoolManager.addTask(new Task()); 
    	}
    	threadPoolManager.shutdown();
    } 
}
