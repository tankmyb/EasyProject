package com.java.jws.myFirst.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;

@WebService(serviceName="HelloService",portName="HelloServicePort",targetNamespace="http://service.why.com/",endpointInterface="com.java.jws.myFirst.server.Hello")
public class HelloImpl implements Hello {

	
	@Override
	public ArrayList<Customer> getCustomerList(){
		ArrayList<Customer> list = new ArrayList<Customer>();
		Customer c1 = new Customer();
		c1.setId(1);
		c1.setName("a1");
		c1.setBirthday(new Date());
		list.add(c1);
		
		Customer c2 = new Customer();
		c2.setId(2);
		c2.setName("a2");
		c2.setBirthday(new Date());
		list.add(c2);
		return list;
		
	}
	
}