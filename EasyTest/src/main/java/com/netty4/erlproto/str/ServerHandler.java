package com.netty4.erlproto.str;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.netty4.erlproto.EchoBuf.Echo;

public class ServerHandler extends SimpleChannelInboundHandler<String> {
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("===============channelActive===========");
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("===============channelInactive===========");
		super.channelInactive(ctx);
	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, String msg)
			throws Exception {
		System.out.println("server:" + "channelRead:" + msg);

		// long size = carInfoReq.getSerializedSize();
		// byte[] buf = carInfoReq.toByteArray();
		 ctx.writeAndFlush(msg);
	}
}