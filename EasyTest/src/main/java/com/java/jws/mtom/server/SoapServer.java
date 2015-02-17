package com.java.jws.mtom.server;

import javax.xml.ws.Endpoint;

public class SoapServer {
	public static void main(String[] args) {  
        Endpoint.publish("http://localhost:8080/helloService",new HelloImpl());  
    }  
}
