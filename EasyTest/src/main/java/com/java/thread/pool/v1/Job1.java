package com.java.thread.pool.v1;

public class Job1 implements IJob{

	@Override
	public void execute(Object data) {
		System.out.println("============job1");
		try {
			Thread.sleep(500L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
