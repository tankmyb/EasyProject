package com.netty.heartbeat.client;
import java.util.Date;

import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;
public class ClientIdleHandler extends IdleStateAwareChannelHandler{
 
 int i = 0;
 
 @Override
 public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent e)
   throws Exception {
	 super.channelIdle(ctx, e);
	 if(e.getState()==IdleState.READER_IDLE){
		 System.out.println("=========readeridle");
	 }
	 if(e.getState()==IdleState.WRITER_IDLE){
		 System.out.println("=========WRITER_IDLE");
	 }
	 if( e.getState() == IdleState.ALL_IDLE){
		 i++;
		 System.out.println(i+",发送心跳"+new Date());
         //System.out.println();("链路空闲！发送心跳！S:{} - C:{} idleState:{}", new Object[]{ctx.getChannel().getRemoteAddress(), ctx.getChannel().getLocalAddress() , e.getState()});
         ChannelFuture f = e.getChannel().write("heartbeat");
         f.addListener(new ChannelFutureListener() {
			
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				i--;
				
			}
		});
         
      }
	  
 }

 
}