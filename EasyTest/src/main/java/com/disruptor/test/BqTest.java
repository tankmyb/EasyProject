package com.disruptor.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BqTest {  
    static boolean ft = true;  
    static boolean z = true;  
    /**  
     * <b>Title:</b> main</br>  
     * <b>Description:</b>   
     * @param args void     
     * @throws:  
     * @author: shenbaise  
     */  
    public static void main(String[] args) {  
        BqTest bt = new BqTest();  
        bt.test();  
    }  
      
      
    public void test(){  
          
        long cost = System.currentTimeMillis();  
        final BlockingQueue<Long> bq = new ArrayBlockingQueue<Long>(4096);  
          
        Runnable p = new Runnable() {  
            public void run() {  
                for(int i= 0;i<100000000;i++){  
                    try {  
                        bq.put((long) i);  
                    } catch (InterruptedException e) {  
                        e.printStackTrace();  
                    }  
                }  
                ft = false;  
            }  
        };  
          
        Runnable c = new Runnable() {  
            public void run() {  
                while(ft || !bq.isEmpty()){  
                    try {  
                        bq.take();  
                    } catch (InterruptedException e) {  
                        e.printStackTrace();  
                    }  
                }  
                z = false;  
            }  
        };  
          
        new Thread(c).start();  
        new Thread(c).start(); 
        new Thread(c).start();  
        new Thread(c).start();  
        new Thread(p).start();  
          
        while(z){  
            try {  
                Thread.sleep(100);  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
        }  
        System.out.println("cost:"+(System.currentTimeMillis() - cost));  
    }  
      
      
}  