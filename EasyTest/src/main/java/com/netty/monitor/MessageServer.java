package com.netty.monitor;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.execution.MemoryAwareThreadPoolExecutor;

public class MessageServer {
	public MemoryAwareThreadPoolExecutor executor;
	public ExecutorService bossExecutor = Executors.newCachedThreadPool();
	public ExecutorService workExecutor = Executors.newCachedThreadPool();
	
	public void run(){
        // Configure the server.
		NioServerSocketChannelFactory factory = 
                new NioServerSocketChannelFactory(bossExecutor,workExecutor);
        ServerBootstrap bootstrap = new ServerBootstrap(factory);
        double coefficient = 0.8;  //系数
    	int numberOfCores = Runtime.getRuntime().availableProcessors();
    	int poolSize = (int)(numberOfCores / (1 - coefficient));

    	executor = new MemoryAwareThreadPoolExecutor(50, 1048576, 1048576);
        // Set up the default event pipeline.
        bootstrap.setPipelineFactory(new MessageServerPipelineFactory(executor));
        // Bind and start to accept incoming connections.
        bootstrap.bind(new InetSocketAddress(9005));
	}
	public MemoryAwareThreadPoolExecutor getExecutor(){
		return executor;
	}
    public static void main(String[] args) throws Exception {
    	final MessageServer server = new MessageServer();
    	server.run();
    	/*new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(5000L);
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
					System.out.println(server.executor.getQueue().size()+"===="+new Date());
					//System.out.println(server.bossExecutor..+"====1");
					//System.out.println(server.executor.getQueue().size()+"====1");
				}
				
			}
		}).start();*/
    }
}