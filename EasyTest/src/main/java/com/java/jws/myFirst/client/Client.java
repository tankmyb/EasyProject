package com.java.jws.myFirst.client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;



public class Client {

	public static void main(String[] args) throws MalformedURLException {
		QName qName = new QName("http://service.why.com/","HelloService");  
        HelloService helloService = new HelloService(new URL("http://127.0.0.1:8080/helloService?wsdl"),qName);  
        Hello hello = (Hello) helloService.getPort(Hello.class);  
        ArrayList list = hello.getCustomerList(); 
	}
}
