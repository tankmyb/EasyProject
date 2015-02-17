package com.java.jws.mythird.server;

import javax.jws.WebService;

@WebService
public interface ServiceDemo {

	public java.util.List<User> listUser();
}
