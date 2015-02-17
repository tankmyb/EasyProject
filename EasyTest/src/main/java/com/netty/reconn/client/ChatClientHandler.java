package com.netty.reconn.client;
import java.util.Date;

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
 private Client client;
 private SendErrorListener listener;
 private static volatile boolean reconn= false;
 public ChatClientHandler(Client client,SendErrorListener listener){
	 this.client = client;
	 this.listener = listener;
 }
 @Override
 public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
   throws Exception {
  System.out.println("进来一个");
 }

 @Override
 public void channelDisconnected(ChannelHandlerContext ctx,
   ChannelStateEvent e) throws Exception {
	// System.out.println("======================");
	 //Logger.getAnonymousLogger().info(e.getFuture().getCause().getMessage());
	/* try {
			Thread.sleep(3000L);
		} catch (InterruptedException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}*/
	 //if(!reconn){
		  //System.out.println("=============reconn=======");
		  //reconn = true;
		  //client.reconn();
	  //}
	 super.channelDisconnected(ctx, e);
	 
   
 }

 @Override
 public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
   throws Exception {
  //Logger.getAnonymousLogger().info(e.getCause().getMessage());
  listener.handlerSendError();
  ctx.getChannel().close();
  //client.reconn();
  // TODO Auto-generated method stub
  super.exceptionCaught(ctx, e);
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
  String msg = (String)e.getMessage();
  //Thread.sleep(2000L);
  System.out.println((this.id++)+"==="+msg+"=="+new Date());
  if(!msg.equals("success")){
	  System.out.println("===========not success");
	  listener.handlerSendError();
  }
  
  super.messageReceived(ctx, e);
  
  //写回给客户端
  //e.getChannel().write("success");
  
 }
 
 
}