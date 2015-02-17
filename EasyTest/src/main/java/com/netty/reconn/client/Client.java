package com.netty.reconn.client;

import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;


public class Client {

	private  Channel channel;
	String host = "127.0.0.1";
	//String host = "115.236.20.170";
    int port = 8082;
    ClientBootstrap bootstrap;
    SendErrorListener listener;
    private volatile boolean conning = true;
    public Client(SendErrorListener listener){
    	this.listener = listener;
    }
	public void init(){
		   
		         // Configure the client.
		         ChannelFactory factory =
		             new NioClientSocketChannelFactory(
		                     Executors.newCachedThreadPool(),
		                     Executors.newCachedThreadPool());
		         Timer trigger=new HashedWheelTimer();  
		          bootstrap = new ClientBootstrap(factory);
		         ChatPipelineClientFactory  cpcf = new ChatPipelineClientFactory(trigger,this,listener);
		         bootstrap.setPipelineFactory(cpcf);
		         //bootstrap.setOption("allIdleTime","5"); //这里，很重要
		         bootstrap.setOption("connectTimeoutMillis","5000");
		         // Start the connection attempt.
		         ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port));

		         // Wait until the connection attempt succeeds or fails.
		         channel = future.awaitUninterruptibly().getChannel();
		        
		         if (!future.isSuccess()) {
		             future.getCause().printStackTrace();
		             conning = false;
		             //System.out.println("====================futurefuturefuturefuture");
		         }
		        // future.getChannel().getCloseFuture().awaitUninterruptibly();
		         //System.out.println("=========close===========");
		         new Thread(new ReconnThread()).start();
		         
	}
	public void send(String msg){
		 //System.out.println(channel.isConnected());
		 if(channel.isConnected()){
			 try{
				 ChannelFuture f = channel.write(msg);
				 //System.out.println(f.awaitUninterruptibly(2000, TimeUnit.MILLISECONDS)+"====await");
				 //System.out.println(f.await(2000L)+"====await");
			 }catch(Exception e){
				 System.out.println("=====================eee");
			 }
			 
		 }else {
			 
			 conning=false;
		 }
	}
	public static void main(String[] args) throws InterruptedException  
    {  
		final Client client = new Client(new SendErrorListener() {
			
			@Override
			public boolean handlerSendError() {
				System.out.println("====handlerSendError======");
				return false;
			}
		});
		client.init();
		 Thread.sleep(2000L);
		 ExecutorService executors = Executors.newFixedThreadPool(100);
		 /*for(int i=0;i<1000000;i++){
			 client.send("heartbeat");
			 //Thread.sleep(2000L);
		 }*/
		 int size=1000000;
		 final CountDownLatch cdl = new CountDownLatch(size);
		 for(int i=0;i<size;i++){
			 executors.execute(new Runnable() {
					
					@Override
					public void run() {
						for(int j=0;j<10;j++){
							//System.out.println("===1==========");
							 client.send("heartbeat");
							 try {
								Thread.sleep(10L);
							} catch (InterruptedException e) {
								// TODO 自动生成的 catch 块
								e.printStackTrace();
							}
						 }
						cdl.countDown();
					}
				});
		 }
		 cdl.await();
		 executors.shutdown();
		 System.out.println("=========finish==============");
		 //System.out.println("=========close before");
		 //channel.getCloseFuture().awaitUninterruptibly();
     // System.out.println("=========close after");
      // We should shut down all thread pools here to exit normally.
      // However, it is just fine to call System.exit(0) because we are
      // finished with the business.
      //System.exit(0);
    }
	public Channel getChannel() {
		return channel;
	}
	class ReconnThread implements Runnable{

		
		
		@Override
		public void run() {
			while(true){
				if(!conning){
					System.out.println("==============re");
					ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port));
					channel = future.awaitUninterruptibly().getChannel();
					if (!future.isSuccess()) {
			            //future.getCause().printStackTrace();
						System.out.println("====fail==1");
						channel.close();
			            //System.exit(0);
			        }else {
			        	conning = true;
			        	
			        }
				}else {
					try {
						Thread.sleep(1000L);
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
			}
			
		}

		
	}

}
