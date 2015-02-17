package com.java.jws.sayHello;

import com.java.jws.sayHello.sys.ServerService;

public class Client {

	 public static void main(String[] args) {  
		 com.java.jws.sayHello.sys.Server hello = new ServerService().getServerPort();  
	        String s = hello.sayHello("why");  
	        System.out.println(s);  
	    }  
}
