package com.easy.kit.utils.log;

public class ErrorTest1 {

	public void error(){
		String a= "1";
		StackTraceElement s = (new Exception()).getStackTrace()[0];
		ErrorLogger.error((new Exception()).getStackTrace()[0], "aaaa");
		
		ErrorLogger.error((new Exception()).getStackTrace()[0], "aaaaasdds");
	}
}
