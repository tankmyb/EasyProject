package com.java.jws.mySecond;

import javax.jws.WebService;

@WebService
public interface ServiceDemo {
	public String hello(String name);

	public java.util.List<String> listUser();
}
