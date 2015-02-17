package com.netty.readtimeout;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.timeout.WriteTimeoutHandler;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;

/**
 * Sends one message when a connection is open and echoes back any received data
 * to the server. Simply put, the echo client initiates the ping-pong traffic
 * between the echo client and server by sending the first message to the
 * server.
 */
public class EchoClient {

	private final String host;
	private final int port;


	public EchoClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void run() throws InterruptedException {
		final Timer timer = new HashedWheelTimer();
		ClientBootstrap bootstrap = new ClientBootstrap(
				new NioClientSocketChannelFactory(
						Executors.newCachedThreadPool(),
						Executors.newCachedThreadPool()));
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			public ChannelPipeline getPipeline() throws Exception {
				return Channels.pipeline(new WriteTimeoutHandler(timer, 1,TimeUnit.MILLISECONDS),new EchoClientHandler());
			}
		});

		ChannelFuture future = bootstrap.connect(new InetSocketAddress(host,
				port));
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		ChannelBuffer firstMessage=ChannelBuffers.buffer(10);
        for (int i = 0; i < firstMessage.capacity(); i ++) {
            firstMessage.writeByte((byte) i);
        }
        for(int i=0;i<10;i++){
        	future.getChannel().write(firstMessage);
        	//Thread.sleep(1000L);
        }
		
		future.getChannel().getCloseFuture().awaitUninterruptibly();
		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
		bootstrap.releaseExternalResources();
	}

	public static void main(String[] args) throws Exception {
		new EchoClient("127.0.0.1",8080).run();
		System.out.println("end....");
	}
}
