package com.netty.async.client;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

import com.netty.async.ReqBean;
import com.netty.async.RespBean;
import com.netty.async.ResponseFuture;
import com.netty.async.ThreadPoolManager;
public class NettyClient {
	static ThreadPoolManager threadPoolManager = ThreadPoolManager.getInstance();
	private int port = 8089;
	private String host = "127.0.0.1";
	private ClientBootstrap bootstrap;
	private ClientHandler handler;
	private ChannelFuture channelFuture;

	public NettyClient() {
		init();
	}

	/**
	 * 初始化客户端
	 */
	private void init() {
		handler = new ClientHandler();
		bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool()));

		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline channelPipeline = Channels.pipeline();
				channelPipeline.addLast("encode", new ObjectEncoder());
				channelPipeline
						.addLast(
								"decode",
								new ObjectDecoder(ClassResolvers
										.cacheDisabled(RespBean.class
												.getClassLoader())));
				channelPipeline.addLast("handler", handler);
				return channelPipeline;
			}
		});
		bootstrap.setOption("connectTimeoutMillis", "5000");
		bootstrap.setOption("tcpNoDelay", true);
		bootstrap.setOption("keepAlive", true);
		bootstrap.setOption("reuseAddress", true);
		channelFuture = bootstrap.connect(new InetSocketAddress(host, port));
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

	public String write(final String str) {
		Future<String> f= threadPoolManager.addTask(new Callable<String>() {

			@Override
			public String call() throws Exception {
				ReqBean reqBean = new ReqBean();
				String uuid = UUID.randomUUID().toString();
				reqBean.setId(uuid);
				reqBean.setReqMsg(str);
				ResponseFuture responseFuture = new ResponseFuture(reqBean);
				channelFuture.getChannel().write(reqBean);
				return responseFuture.get(uuid);
			}
		});
		try {
			return f.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setBootstrap(ClientBootstrap bootstrap) {
		this.bootstrap = bootstrap;
	}

	public void setHandler(ClientHandler handler) {
		this.handler = handler;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public ClientHandler getHandler() {
		return handler;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public ChannelFuture getChannelFuture() {
		return channelFuture;
	}

	public static void mulitThread(final NettyClient client) {
       ExecutorService exec = Executors.newCachedThreadPool();
       for(int i=0;i<1000;i++){
    	   exec.submit(new Runnable() {
			
			@Override
			public void run() {
				String retVal = client.write(UUID.randomUUID().toString());
				System.out.println("retVal:" + retVal+"==="+new Date());
			}
		});
       }
	}

	public static void singleThread(NettyClient client) {
		for (int i = 0; i < 10; i++) {
			String retVal = client.write("aaaaaa" + i);
			System.out.println("retVal:" + retVal);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		NettyClient client = new NettyClient();
		Thread.sleep(2000L);
		mulitThread(client);
		System.out.println("================end");
	}
}