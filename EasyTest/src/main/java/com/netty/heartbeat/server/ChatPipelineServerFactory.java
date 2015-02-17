package com.netty.heartbeat.server;

import static org.jboss.netty.channel.Channels.pipeline;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.HashedWheelTimer;

import com.netty.message.MessageDecoder;
import com.netty.message.MessageEncoder;

public class ChatPipelineServerFactory implements ChannelPipelineFactory {

	private HashedWheelTimer idleTimer = new HashedWheelTimer();


	@Override
	public ChannelPipeline getPipeline() throws Exception {

		ChannelPipeline pipeline = pipeline();

		// 添加netty默认支持的 编解码器(可自动添加包头，并处理粘包问题)

		pipeline.addLast("decoder", new MessageDecoder());
		pipeline.addLast("encoder", new MessageEncoder());
		Integer writeIdleTime = 10;
		Integer readIdleTime = 30;
		writeIdleTime = 10;
		// 默认为写空闲的3倍
		readIdleTime = writeIdleTime * 3;

		//pipeline.addLast("timeout", new IdleStateHandler(idleTimer,
				//readIdleTime, writeIdleTime, 0));
		//pipeline.addLast("idleHandler", new ServerStateCheckChannelHandler());

		pipeline.addLast("handler", new ChatServerHandler());// 将编写好的服务器端的handler添加到这里

		return pipeline;
	}

}