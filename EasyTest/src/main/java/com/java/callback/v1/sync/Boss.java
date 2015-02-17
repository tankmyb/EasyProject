package com.java.callback.v1.sync;

public class Boss {

	public void want(Wang wang) throws InterruptedException{
		wang.doSomething(this);
	}
	public void tell(String msg){
		System.out.println(msg);
	}
}
