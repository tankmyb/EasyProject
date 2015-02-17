package com.netty.readtimeout;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;
/**
 * Handler implementation for the echo server.
 */
public class EchoServerHandler extends IdleStateAwareChannelHandler  {

	private static final Logger logger = Logger
			.getLogger(EchoServerHandler.class.getName());


	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		System.out.println("server has been connected");
		super.channelConnected(ctx, e);
		
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		// Send back the received message to the remote peer.
		
		System.out
				.println("I an server ,I received a message,and I will received a message after 5 mill later");
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		e.getChannel().write(e.getMessage());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		// Close the connection when an exception is raised.
		System.out.println(" Close the connection when an exception is raised"+e.getCause().getMessage());
		logger.log(Level.WARNING, "Unexpected exception from downstream.",
				e.getCause());
		e.getChannel().close();
	}
	
	@Override
	public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent e)
			throws Exception {
		
//		super.channelIdle(ctx, e);
		/*if( e.getState() == IdleState.ALL_IDLE){  
           e.getChannel().write("str123".getBytes());  
            super.channelIdle(ctx, e);      
         }  */
	}
	
	
}
