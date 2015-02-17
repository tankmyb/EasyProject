package com.netty.udp.split.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ConnectionlessBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.DatagramChannel;
import org.jboss.netty.channel.socket.nio.NioDatagramChannelFactory;
import org.jboss.netty.handler.codec.frame.FixedLengthFrameDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

public class Server {

	private ConnectionlessBootstrap b;
	private DatagramChannel datagramChannel;

	public Server(int port) {

		init(port);
	}

	private void init(int port) {
		b = new ConnectionlessBootstrap(new NioDatagramChannelFactory(
				Executors.newCachedThreadPool()));

		b.setOption("tcpNoDelay", true);
		b.setOption("receiveBufferSize", 1048576); // 1M

		b.setPipelineFactory(new ChannelPipelineFactory() {
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("decoder", new UDPDecoder());
				pipeline.addLast("encoder", new StringEncoder());
				pipeline.addLast("logic", new ServerHandler());

				return pipeline;
			}
		});

		datagramChannel = (DatagramChannel) b.bind(new InetSocketAddress(port));
		System.out.println(" Server is starting ……");
	}

	public void writeString(String message, String remoteHost, int remotePort) {
		datagramChannel.write(message, new InetSocketAddress(remoteHost,
				remotePort));
	}

	public void shutdown() {
		if (datagramChannel != null) {
			datagramChannel.close();
		}
		if (b != null) {
			b.releaseExternalResources();
		}
	}

	public static void main(String[] args) {
        new Server(9001);
	}
}
