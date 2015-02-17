package com.netty.heartbeat.client;
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


public class ChatClientHandler extends SimpleChannelHandler{

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
   //super.channelDisconnected(ctx, e);
 }

 @Override
 public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
   throws Exception {
  Logger.getAnonymousLogger().info(e.getCause().getMessage());
  //ctx.getChannel().close();
  // TODO Auto-generated method stub
  //super.exceptionCaught(ctx, e);
 }

 @Override
 public void childChannelClosed(ChannelHandlerContext ctx,
   ChildChannelStateEvent e) throws Exception {
  System.out.println("===close");
  super.childChannelClosed(ctx, e);
 }

 @Override
 public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
   throws Exception {
  //System.out.println(this.id++);
  System.out.println(e.getMessage());
  
  
  //写回给客户端
  //e.getChannel().write("success");
  
 }
 
 
}