package com.netty.heartbeat.client;

import java.util.Date;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title: StateCheckChannelHandler.java
 * @Package org.summercool.hsf.netty.channelhandler
 * @Description: 通道状态检测
 * @author 简道
 * @date 2011-9-17 上午10:12:13
 * @version V1.0
 */
public class ClientStateCheckChannelHandler extends IdleStateAwareChannelHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	public ClientStateCheckChannelHandler() {
	}

	@Override
	public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent e) throws Exception {
		System.out.println("=========channelIdle================="+new Date());
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
