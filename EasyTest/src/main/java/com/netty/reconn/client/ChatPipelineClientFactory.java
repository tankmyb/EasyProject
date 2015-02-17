package com.netty.reconn.client;
import static org.jboss.netty.channel.Channels.pipeline;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.handler.timeout.WriteTimeoutHandler;
import org.jboss.netty.util.Timer;

import com.netty.message.MessageDecoder;
import com.netty.message.MessageEncoder;

public class ChatPipelineClientFactory implements ChannelPipelineFactory{
 
 private Timer timer;
 
 private Client client;
 private SendErrorListener listener;
 public ChatPipelineClientFactory(Timer timer,Client client,SendErrorListener listener){
  this.client = client;
  this.timer = timer;
  this.listener = listener;
 }
 
 @Override
 public ChannelPipeline getPipeline() throws Exception {
  
  ChannelPipeline pipeline = pipeline();
  
  //添加netty默认支持的 编解码器(可自动添加包头，并处理粘包问题)

  pipeline.addLast("decoder", new MessageDecoder());
	pipeline.addLast("encoder", new MessageEncoder());
	 //pipeline.addLast("timeout", new IdleStateHandler(timer, 0, 0, 10));
     //pipeline.addLast("idleHandler", new ClientIdleHandler());
	//pipeline.addLast("writeTimeout", new WriteTimeoutHandler(this.timer,10));
  pipeline.addLast("handler", new ChatClientHandler(client,this.listener));//将编写好的服务器端的handler添加到这里 
  
  
  return pipeline;
 }

}