package com.netty.readtimeout;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;


/**
 * Handler implementation for the echo client. It initiates the ping-pong
 * traffic between the echo client and server by sending the first message to
 * the server.
 */
public class EchoClientHandler extends SimpleChannelUpstreamHandler {

	private static final Logger logger = Logger
			.getLogger(EchoClientHandler.class.getName());


	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
		
	}


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		// Close the connection when an exception is raised.
		System.out.println("close the connection when an exception is raised");
		logger.log(Level.WARNING, "Unexpected exception from downstream.",
				e.getCause());
		e.getChannel().close();
	}


	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		System.out.println("=======================");
		// TODO 自动生成的方法存根
		super.messageReceived(ctx, e);
	}
}