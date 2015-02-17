package com.netty.simple;
  
/** 
 * TODO 
 * Administrator 2013-3-23下午07:50:49 
 */  
public class RunClient {  
      
    public static void main(String[] args) {  
          
          
        
        ClientThread client = new ClientThread();  
          
        client.init();  
        client.start();  
    }  
}  