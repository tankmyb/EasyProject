package com.netty.reconn.server;
import java.util.logging.Logger;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ChildChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;


public class ChatServerHandler extends SimpleChannelHandler{

 public static final ChannelGroup channelGroup = new DefaultChannelGroup();
 
 public int id;
 
 @Override
 public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
   throws Exception {
  System.out.println("进来一个");
 }

 @Override
 public void channelDisconnected(ChannelHandlerContext ctx,
   ChannelStateEvent e) throws Exception {
   super.channelDisconnected(ctx, e);
 }

 @Override
 public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
   throws Exception {
  //Logger.getAnonymousLogger().info(e.getCause().getMessage());
	 e.getCause().printStackTrace();
  ctx.getChannel().close();
  // TODO Auto-generated method stub
  //super.exceptionCaught(ctx, e);
 }

 @Override
 public void childChannelClosed(ChannelHandlerContext ctx,
   ChildChannelStateEvent e) throws Exception {
  
  super.childChannelClosed(ctx, e);
 }

 @Override
 public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
   throws Exception {
 // System.out.println(this.id++);
  String message = (String)e.getMessage();
  long s = Math.round(Math.random()*2500+1000);
  System.out.println(s);
  Thread.sleep(s);
  if(message.equals("heartbeat")){
	//写回给客户端
	  e.getChannel().write("success");
  }
  super.messageReceived(ctx, e);
  
  
  
 }
 
 
}