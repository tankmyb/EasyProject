package com.netty.message;

import java.util.Date;

public class HelloWordMain  
{  
    public static void main(String[] args) throws InterruptedException  
    {  
        ClientThread r = new ClientThread();  
        Thread t = new Thread(r);  
        t.setName("client thread");  
        t.start();  
        Thread.sleep(2000l);
        for(int i=0;i<1;i++)
        {  
            r.sendMsg("45555"); 
            Thread.sleep(5000L);
            System.out.println(new Date());
        }  
         
    }  
}  
