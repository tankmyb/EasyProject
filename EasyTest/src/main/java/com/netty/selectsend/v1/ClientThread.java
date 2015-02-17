package com.netty.selectsend.v1;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
  
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
    	//192.168.4.22
    	 String host = "127.0.0.1";
         int port = 9001;
         // Configure the client.
         ClientBootstrap bootstrap = new ClientBootstrap(
                 new NioClientSocketChannelFactory(
                         Executors.newCachedThreadPool(),
                         Executors.newCachedThreadPool()));
         // Set up the event pipeline factory.
         bootstrap.setPipelineFactory(new MessageClientPipelineFactory());
         // Start the connection attempt.
         future = bootstrap.connect(new InetSocketAddress(host, port));
         future.awaitUninterruptibly();
    }  
      
    /** 
     * 发送消息至server 
     */  
    public void sendMsg(String s)  
    {  
        if(future==null) return;  
        //System.out.println("======="+future.getChannel().isConnected());
        System.out.println(future.getChannel().getLocalAddress()+"==send");
        future.getChannel().write(s); 
        
    }  
      
}  
