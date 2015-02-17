package com.java.jws.mythird.server;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@WebService(targetNamespace = "http://server.hw.demo/", 
		endpointInterface = "com.java.jws.mythird.server.ServiceDemo", 
		serviceName = "ServiceDemo",
		portName="ServiceDemopott")
public class ServiceDemoImpl implements ServiceDemo {
	Log log = LogFactory.getLog(ServiceDemoImpl.class);

	public ServiceDemoImpl() {
		log.info("初始化");
	}


	public List<User> listUser() {
		List<User> list = new ArrayList<User>();
		User u1 = new User();
		u1.setId(1);
		u1.setName("u1");
		list.add(u1);
		
		User u2 = new User();
		u2.setId(2);
		u2.setName("u2");
		list.add(u2);
		return list;
	}
}

