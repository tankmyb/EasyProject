package com.java.jws.myFirst.server;

import javax.xml.ws.Endpoint;

public class SoapServer {
	public static void main(String[] args) {
		Endpoint endpoint = Endpoint.publish("http://127.0.0.1:8080/helloService",new HelloImpl());
	}
}
