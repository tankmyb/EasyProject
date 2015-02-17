package com.netty.heartbeat.client;


public class HelloWordMain  
{  
    public static void main(String[] args) throws InterruptedException  
    {  
        ClientThread r = new ClientThread();  
        Thread t = new Thread(r);  
        t.setName("client thread");  
        t.start();  
        Thread.sleep(2000l);
        for(int i=0;i<10;i++)
        {  
        	System.out.println(i+"=========");
            r.sendMsg(i+""); 
        }  
         
    }  
}  
