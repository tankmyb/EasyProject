package com.java.jmx.HelloWorld;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

import com.java.jmx.Hello;
import com.sun.jdmk.comm.HtmlAdaptorServer;

public class HelloAgent {
	public static void main(String[] args) throws Exception {
		MBeanServer server = MBeanServerFactory.createMBeanServer();
		ObjectName helloName = new ObjectName("chengang:name=HelloWorld");
		server.registerMBean(new Hello(), helloName);
		ObjectName adapterName = new ObjectName(
				"HelloAgent:name=htmladapter,port=8082");
		HtmlAdaptorServer adapter = new HtmlAdaptorServer();
		server.registerMBean(adapter, adapterName);
		adapter.start();
		System.out.println("start.....");
	}
}
