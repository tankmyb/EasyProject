package com.netty.reconn.server;
import static org.jboss.netty.channel.Channels.*;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import org.jboss.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.Timer;

import com.netty.message.MessageDecoder;
import com.netty.message.MessageEncoder;

public class ChatPipelineServerFactory implements ChannelPipelineFactory{
 
 private Timer timer;
 
 public ChatPipelineServerFactory(Timer timer){
  
  this.timer = timer;
 }
 
 @Override
 public ChannelPipeline getPipeline() throws Exception {
  
  ChannelPipeline pipeline = pipeline();
  
  //添加netty默认支持的 编解码器(可自动添加包头，并处理粘包问题)

  pipeline.addLast("decoder", new MessageDecoder());
	pipeline.addLast("encoder", new MessageEncoder());
   // pipeline.addLast("timeout", new IdleStateHandler(timer, 0, 0, 10));//此两项为添加心跳机制 10秒查看一次在线的客户端channel是否空闲，IdleStateHandler为netty jar包中提供的类
  //pipeline.addLast("hearbeat", new Heartbeat());//此类 实现了IdleStateAwareChannelHandler接口

  pipeline.addLast("handler", new ChatServerHandler());//将编写好的服务器端的handler添加到这里 
  
  
  return pipeline;
 }

}