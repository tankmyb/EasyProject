package com.java.callback.v1.sync;

public class Wang {

	public void doSomething(Boss boss) throws InterruptedException{
		
		Thread.sleep(2000L);
		boss.tell("finished");
	}
}
