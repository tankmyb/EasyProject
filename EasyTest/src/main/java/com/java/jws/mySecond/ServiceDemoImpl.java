package com.java.jws.mySecond;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@WebService(targetNamespace = "http://server.hw.demo/", 
		endpointInterface = "com.java.jws.mySecond.ServiceDemo", 
		serviceName = "ServiceDemo",
		portName="ServiceDemopott")
public class ServiceDemoImpl implements ServiceDemo {
	Log log = LogFactory.getLog(ServiceDemoImpl.class);
	List<String> list = new ArrayList<String>();

	@Resource
	private WebServiceContext wsContext;

	public ServiceDemoImpl() {
		log.info("初始化");
		System.out.println("init....");
		for (int i = 0; i < 10; i++) {
			list.add("第" + i + "个");
		}
	}

	public String hello(String name) {
		MessageContext message = wsContext.getMessageContext();
		message.get("");
		System.out.println("message:" + message.keySet());
		return "hi :" + name;
	}

	public List<String> listUser() {
		return list;
	}
}

