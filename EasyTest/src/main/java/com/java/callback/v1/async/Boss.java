package com.java.callback.v1.async;

import java.util.Date;

public class Boss implements Callback{

	@Override
	public void toDo() {
		System.out.println("小王完成了"+new Date());
	}
    public void doOther(){
    	System.out.println("老板做其它事了");
    }
	
}
