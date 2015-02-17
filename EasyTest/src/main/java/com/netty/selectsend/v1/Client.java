package com.netty.selectsend.v1;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

public class Client {

	public static void main(String[] args) {
		  
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
         ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port));
         future.awaitUninterruptibly();
    
	}
}
