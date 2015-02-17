package com.java.juc.executor.cachedThreadPool;

public class NoExecuter {
	
	public static void main(String[] args) {
		for(int i=0;i<50;i++){
			//new Thread(new MyThread(i)).start();
		}
	}
}
