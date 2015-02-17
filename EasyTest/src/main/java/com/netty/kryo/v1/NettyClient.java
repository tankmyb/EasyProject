package com.netty.kryo.v1;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import com.netty.kryo.CompressionDownstreamHandler;
import com.netty.kryo.DecompressionUpstreamHandler;
import com.netty.kryo.DeserializeUpstreamHandler;
import com.netty.kryo.LengthBasedDecoder;
import com.netty.kryo.LengthBasedEncoder;
import com.netty.kryo.SerializeDownstreamHandler;
import com.netty.kryo.pojo.Person;

public class NettyClient {
	private int port = 8081;
	private String host = "127.0.0.1";
	private ClientBootstrap bootstrap;
	private ChannelFuture channelFuture;

	/**
	 * 初始化客户端
	 */
	public void init() {
		bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(
				Executors.newFixedThreadPool(1), // boss 监听请求，并分派给slave进行处理
				Executors.newFixedThreadPool(1)));

		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
				
				pipeline.addLast("encode", new KryoFrameEncoder());//以下downstreamhandler（输出）顺序执行
				pipeline.addLast("serialize", new KryoEncoder());//使用了KryoSerializer进行序列化
				pipeline.addLast("decode", new KryoFrameDecoder());//以下upstreamhandler（输入）逆序执行
				pipeline.addLast("deserialize", new KryoDecoder());//使用了KryoSerializer进行反序列化序列化
				
				
				pipeline.addLast("handler", new ClientHandler());
				return pipeline;
			}
		});
		bootstrap.setOption("connectTimeoutMillis", "5000");
		//bootstrap.setOption("tcpNoDelay", true);
		//bootstrap.setOption("keepAlive", true);
		//bootstrap.setOption("reuseAddress", true);
	}

	public void start() {
		channelFuture = bootstrap.connect(new InetSocketAddress(host, port));
		System.out.println("连接远程服务器" + host + ":" + port + "端口成功，你现在可以开始发消息了。");
	}

	public void write(Person p) {
		channelFuture.getChannel().write(p);
	}

	public void stop() {
		channelFuture.awaitUninterruptibly();
		if (!channelFuture.isSuccess()) {
			channelFuture.getCause().printStackTrace();
		}
		// 等待或者监听数据全部完成
		channelFuture.getChannel().getCloseFuture().awaitUninterruptibly();
		// 释放连接资源
		bootstrap.releaseExternalResources();

	}

	public void setBootstrap(ClientBootstrap bootstrap) {
		this.bootstrap = bootstrap;
	}

	
	public void setPort(int port) {
		this.port = port;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public ChannelFuture getChannelFuture() {
		return channelFuture;
	}

	public static void main(String[] args) throws InterruptedException {
		NettyClient client = new NettyClient();
		client.init();
		client.start();
		Thread.sleep(1000L);
		Long start = System.currentTimeMillis();
		for (int i = 0; i < 2000000; i++) {
			String line = i+"";
			Person owner = new Person();
			owner.setEmail(line);
			owner.setId(1);
			owner.setName("aaaaaaaa");
			client.write(owner);
			//Thread.sleep(1);
			/*if(i%100000==0){
				Thread.sleep(1);
			}*/
		}
		System.out.println(System.currentTimeMillis() - start);
	}
}