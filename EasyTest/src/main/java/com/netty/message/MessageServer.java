package com.netty.message;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.execution.MemoryAwareThreadPoolExecutor;

public class MessageServer {
	public ExecutorService bossExecutor = Executors.newCachedThreadPool();
	public ExecutorService workExecutor = Executors.newCachedThreadPool();
	
	public void run(){
        // Configure the server.
        ServerBootstrap bootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(bossExecutor,workExecutor));
        // Set up the default event pipeline.
        bootstrap.setPipelineFactory(new MessageServerPipelineFactory());
 
        // Bind and start to accept incoming connections.
        bootstrap.bind(new InetSocketAddress(9001));
	}
    public static void main(String[] args) throws Exception {
    	final MessageServer server = new MessageServer();
    	server.run();
    }
}