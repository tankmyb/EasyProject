package com.netty4.helloworld;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class HelloClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
    	ctx.writeAndFlush("channelRegistered");
		super.channelRegistered(ctx);
	}

	@Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        
        System.out.println("Server say : " + msg);
    }
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client active "+ctx.channel().remoteAddress());
        ctx.writeAndFlush("channelActive"+ "\r\n");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client close ");
        super.channelInactive(ctx);
    }
}