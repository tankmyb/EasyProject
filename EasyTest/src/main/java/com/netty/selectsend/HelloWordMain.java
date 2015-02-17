package com.netty.selectsend;

public class HelloWordMain  
{  
    public static void main(String[] args) throws InterruptedException  
    {  
        ClientThread r = new ClientThread("b");  
        Thread t = new Thread(r);  
        t.setName("client thread");  
        t.start();  
        Thread.sleep(10000);
        //for(int i=0;i<=1;i++)
        //{  
            r.sendMsg("a,hello a"); 
            r.sendMsg("b,hello b"); 
       // }  
         
    }  
}  
