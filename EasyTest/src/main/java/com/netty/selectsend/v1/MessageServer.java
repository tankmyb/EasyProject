package com.netty.selectsend.v1;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

public class MessageServer {
	 
    public static void main(String[] args) throws Exception {
        // Configure the server.
        ServerBootstrap bootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));
 
        // Set up the default event pipeline.
        bootstrap.setPipelineFactory(new MessageServerPipelineFactory());
 
        // Bind and start to accept incoming connections.
        bootstrap.bind(new InetSocketAddress(9001));
 
        new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(5000L);
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
					System.out.println(MessageServerHandler.channels);
				}
				
			}
		}).start();
    }
}