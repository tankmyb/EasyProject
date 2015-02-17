package com.netty.heartbeat.client;
import static org.jboss.netty.channel.Channels.pipeline;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;

import com.netty.message.MessageDecoder;
import com.netty.message.MessageEncoder;

public class ChatPipelineClientFactory implements ChannelPipelineFactory{
 
 private Timer timer;
 private HashedWheelTimer idleTimer = new HashedWheelTimer();

 public ChatPipelineClientFactory(Timer timer){
  
  this.timer = timer;
 }
 
 @Override
 public ChannelPipeline getPipeline() throws Exception {
  
  ChannelPipeline pipeline = pipeline();
  
  //添加netty默认支持的 编解码器(可自动添加包头，并处理粘包问题)

  pipeline.addLast("decoder", new MessageDecoder());
	pipeline.addLast("encoder", new MessageEncoder());
	Integer writeIdleTime = 10;
	Integer readIdleTime = 60;

	pipeline.addLast("timeout", new IdleStateHandler(idleTimer,
			readIdleTime, writeIdleTime, 0));
	pipeline.addLast("idleHandler", new ClientStateCheckChannelHandler());

  pipeline.addLast("handler", new ChatClientHandler());//将编写好的服务器端的handler添加到这里 
  
  
  return pipeline;
 }

}