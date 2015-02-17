package com.netty.udp.v2;

public class Client {
	public static void main(String[] args) throws InterruptedException {  
	    NettyUdp udp = new NettyUdp(1001, 100);  
	    StringBuffer sb = new StringBuffer();
        for(int i=0;i<=2000;i++)
        {  
        	sb.append(i+" ");
        } 
	    for(int i=0; i<1; i++){  
	        udp.writeString(sb.toString(), "127.0.0.1", 1000);  
	    }  
	    Thread.sleep(2000L);
	       udp.shutdown();  
	}  
}
