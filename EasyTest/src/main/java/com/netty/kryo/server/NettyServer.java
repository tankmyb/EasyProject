package com.netty.kryo.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import com.netty.kryo.CompressionDownstreamHandler;
import com.netty.kryo.DecompressionUpstreamHandler;
import com.netty.kryo.DeserializeUpstreamHandler;
import com.netty.kryo.LengthBasedDecoder;
import com.netty.kryo.LengthBasedEncoder;
import com.netty.kryo.SerializeDownstreamHandler;

public class NettyServer {
	private int port = 8081;
	private ServerBootstrap bootstrap;
	private ServerHandler handler;

	/**
	 * 初始化服务器端
	 */
	public void init() {
		handler = new ServerHandler();
		bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
				Executors.newFixedThreadPool(1), // boss 监听请求，并分派给slave进行处理
				Executors.newFixedThreadPool(1)// slave 处理请求，将其丢到线程池中处理
				));

		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("encode", new LengthBasedEncoder());//以下downstreamhandler（输出）顺序执行
				//pipeline.addLast("compress", new CompressionDownstreamHandler());
				pipeline.addLast("serialize", new SerializeDownstreamHandler());//使用了KryoSerializer进行序列化

				pipeline.addLast("decode", new LengthBasedDecoder());//以下upstreamhandler（输入）逆序执行
				//pipeline.addLast("decompress", new DecompressionUpstreamHandler());
				pipeline.addLast("deserialize", new DeserializeUpstreamHandler());//使用了KryoSerializer进行反序列化序列化
				/* 添加自定义的handler，对请求进行处理 */
				pipeline.addLast("handler", handler);
				return pipeline;
			}
		});
		bootstrap.setOption("allIdleTime", "5");
		/* 使用tcp长连接 */
		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
		bootstrap.setOption("reuseAddress", true);
	}

	/**
	 * 绑定端口，启动netty服务
	 */
	public void start() {
		bootstrap.bind(new InetSocketAddress(port));
		System.out.println("服务器启动,端口:" + port);
	}

	/**
	 * 关闭netty，释放资源。
	 */
	public void stop() {
		bootstrap.releaseExternalResources();
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setHandler(ServerHandler handler) {
		this.handler = handler;
	}

	public static void main(String[] args) {
		NettyServer server = new NettyServer();
		server.init();
		server.start();
	}
}