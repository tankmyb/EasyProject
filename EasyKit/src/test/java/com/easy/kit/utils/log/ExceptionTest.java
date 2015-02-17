package com.easy.kit.utils.log;

public class ExceptionTest {

	public void exception(){
		//String[] a = new String[2];
		//a[4]="aaa";
		ExceptionLogger.exception(new RuntimeException("test"));
	}
}
