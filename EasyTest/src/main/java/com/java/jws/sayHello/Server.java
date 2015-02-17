package com.java.jws.sayHello;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;

/**
 * 
 * 一、进入src目录
 * 二、执行 wsimport -p com.java.jws.sayHello.sys -keep http://localhost:8080/com.java.jws.sayHello.Server?wsdl
 * 三、sys目录
 */
@WebService
public class Server {

	public String sayHello(String name) {  
        return "Hello " + name;  
    }  
  
    public static void main(String[] args){  
        Endpoint.publish("http://localhost:8080/com.java.jws.sayHello.Server", new Server());  
        System.out.println("Success");  
    }  
}
