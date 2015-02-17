package com.netty.simple;
import java.util.Scanner;  
  

import java.util.concurrent.TimeUnit;

import org.jboss.netty.channel.Channel;  
import org.jboss.netty.channel.ChannelFuture;
  
public class ClientThread extends Thread {  
    private NettyClient nettyClient= new NettyClient();  
  
    private Scanner scanner = new Scanner(System.in);  
  
    public void init() {  
        nettyClient.init();  
        nettyClient.start();  
    }  
  
    public void run() {  
        while(true) {  
  
            Channel channel = nettyClient.getChannelFuture().getChannel();  
            
            ChannelFuture f = channel.write("aaaaaa");  
            f.awaitUninterruptibly(10, TimeUnit.SECONDS);
            try {
				Thread.sleep(10L);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
        }  
    }  
  
    public void setNettyClient(NettyClient nettyClient) {  
        this.nettyClient = nettyClient;  
    }  
  
}  