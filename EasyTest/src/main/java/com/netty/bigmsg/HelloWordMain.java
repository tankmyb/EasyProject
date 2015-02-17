package com.netty.bigmsg;

import com.utils.ObjectSizeUtil;

public class HelloWordMain  
{  
	
    public static void main(String[] args) throws InterruptedException  
    {  
    	for(int i=0;i<10;i++){
    		new Thread(new Runnable() {
				
				@Override
				public void run() {
					ClientThread r = new ClientThread();  
			        Thread t = new Thread(r);  
			        t.setName("client thread");  
			        t.start();  
			        try {
						Thread.sleep(2000l);
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
			        StringBuffer sb = new StringBuffer();
			        for(int i=0;i<=2000;i++)
			        {  
			        	sb.append(Thread.currentThread().getId()+" ");
			        }  
			        System.out.println(sb.length());
			        r.sendMsg(sb.toString());
			        System.out.println(ObjectSizeUtil.size(sb));
					
				}
			}).start();
    	}
        
    }  
}  
