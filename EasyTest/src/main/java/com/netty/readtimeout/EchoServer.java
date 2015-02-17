package com.netty.readtimeout;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

/**
 * Echoes back any received data from a client.
 */
public class EchoServer {

	private final int port;

	public EchoServer(int port) {
		this.port = port;
	}

	public void run() {
		// Configure the server.
		ServerBootstrap bootstrap = new ServerBootstrap(
				new NioServerSocketChannelFactory(
						Executors.newCachedThreadPool(),
						Executors.newCachedThreadPool()));

		ChannelPipelineFactory pipelineFactory = new MyPipelineFactory(
				new EchoServerHandler());
		bootstrap.setPipelineFactory(pipelineFactory);
//		bootstrap.setOption("allIdleTime", "10");
		
		bootstrap.bind(new InetSocketAddress(port));
	}

	public static void main(String[] args) throws Exception {
		int port;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		} else {
			port = 8080;
		}
		new EchoServer(port).run();
	}
}