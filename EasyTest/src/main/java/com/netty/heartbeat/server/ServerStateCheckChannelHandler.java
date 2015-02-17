package com.netty.heartbeat.server;

import java.util.Date;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerStateCheckChannelHandler extends IdleStateAwareChannelHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	public ServerStateCheckChannelHandler() {
	}

	@Override
	public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent e) throws Exception {
		System.out.println("=========channelIdle====server============="+new Date());
		if (e.getState() == IdleState.WRITER_IDLE) {
				System.out.println("=========WRITER_IDLE================="+new Date());
				e.getChannel().write("heartbeat");
		} else if (e.getState() == IdleState.READER_IDLE) {
			System.out.println("=========READER_IDLE================="+new Date());
			logger.error("channel:{} is time out.", e.getChannel());

			e.getChannel().close();
			//
		}
		super.channelIdle(ctx, e);
	}

	
}
