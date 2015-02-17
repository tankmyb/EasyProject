package com.java.juc.callable;

import java.util.Date;

class Handler implements Runnable{

	private int i;
	public Handler(int i){
		this.i = i;
	}
	@Override
	public void run() {
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		//if(i%100==0){
			System.out.println(i);
		//}
	}
}
public class TestTaskSize {

	public static void main(String[] args) {
		final ThreadPoolManager poolManger = ThreadPoolManager.getInstance();
		/*new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(5000L);
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
					poolManger.getTaskSize();
				}
			}
		}).start();*/
		for(int i=0;i<10000;i++){
			if(!poolManger.execute(new Handler(i))){
				break;
			};
		}
		
	}
}
