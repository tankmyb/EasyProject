package com.netty.pojo.client;

public class TestMain  
{  
    public static void main(String[] args) throws InterruptedException  
    {  
        ClientThread r = new ClientThread();  
        Thread t = new Thread(r);  
        t.setName("client thread");  
        t.start();  
         Thread.sleep(2000l);
        for(int i=0;i<100;i++)  
        {  
            try  
            {  
                Thread.sleep(3);  
            } catch (InterruptedException e)  
            {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
            if(!r.sendMsg()){
            	break;
            }
        }  
         
    }  
}  