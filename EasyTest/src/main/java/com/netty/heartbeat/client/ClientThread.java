package com.netty.heartbeat.client;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;
  
/** 
 * 启动一个client线程，用来间歇性的发送消息 
 * @author Ransom 
 */  
public class ClientThread implements Runnable  
{  
   private ChannelFuture future;  
    public ChannelFuture getFuture()  
{  
    return future;  
}  
  
public void setFuture(ChannelFuture future)  
{  
    this.future = future;  
}  
  
    @Override  
    public void run()  
    {  
    	 String host = "127.0.0.1";
         int port = 6666;
      // Configure the client.
         ChannelFactory factory =
             new NioClientSocketChannelFactory(
                     Executors.newCachedThreadPool(),
                     Executors.newCachedThreadPool());
         Timer trigger=new HashedWheelTimer();  
         ClientBootstrap bootstrap = new ClientBootstrap(factory);
         ChatPipelineClientFactory  cpcf = new ChatPipelineClientFactory(trigger);
         bootstrap.setPipelineFactory(cpcf);
         //bootstrap.setOption("allIdleTime","5"); //这里，很重要
         // Start the connection attempt.
          future = bootstrap.connect(new InetSocketAddress(host, port));

    }  
      
    /** 
     * 发送消息至server 
     */  
    public void sendMsg(String s)  
    {  
        if(future==null) return;  
        //System.out.println("=======");
        future.getChannel().write(s);  
    }  
      
}  
