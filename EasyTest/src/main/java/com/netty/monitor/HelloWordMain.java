package com.netty.monitor;

public class HelloWordMain  
{  
    public static void main(String[] args) throws InterruptedException  
    {  
        ClientThread r = new ClientThread();  
        Thread t = new Thread(r);  
        t.setName("client thread");  
        t.start();  
        Thread.sleep(2000l);
        for(int i=0;i<=1000000;i++)
        {  
            r.sendMsg(i+""); 
        }  
         
    }  
}  
