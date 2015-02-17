package com.java.callback.v1.async;

import java.util.Date;

public class Wang{

	private Callback boss;
	
	public Wang(Callback boss){
		this.boss = boss;
	}
	public void work(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("小王开始做事"+new Date());
				try {
					Thread.sleep(5000L);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				boss.toDo();
				
			}
		}).start();
	}

}
