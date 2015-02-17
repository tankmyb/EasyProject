package com.java.jws.mySecond;

import javax.xml.ws.Endpoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Server {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String address = "http://localhost:9898/ServiceDemo";
		ServiceDemoImpl implementor = new ServiceDemoImpl();
		Endpoint.publish(address, implementor);
		Log log = LogFactory.getLog(Server.class);
		log.info("服务器启动...");
	}
}

