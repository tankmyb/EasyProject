package com.java.jws.noCreate.server;

import javax.xml.ws.Endpoint;  

/** 
 *  
 * @author why 
 * 
 */  
public class SoapServer {  
    public static void main(String[] args) {  
        Endpoint.publish("http://localhost:8080/helloService",new HelloImpl());  
  
    }  
} 
