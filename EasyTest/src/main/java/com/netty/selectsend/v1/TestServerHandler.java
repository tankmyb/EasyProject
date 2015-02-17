package com.netty.selectsend.v1;

import org.jboss.netty.channel.ChannelHandler.Sharable;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

@Sharable
public class TestServerHandler extends SimpleChannelUpstreamHandler {
 
	
    @Override
    public void exceptionCaught(
            ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
    	System.out.println("==========exceptionCaught=======1111==========");
        e.getChannel().close();
        super.exceptionCaught(ctx, e);
    }
    @Override
	public void channelDisconnected(ChannelHandlerContext ctx,
			ChannelStateEvent e) throws Exception {
		System.out.println("=========channelDisconnected====11==========");
		System.out.println(e.getChannel());
		// TODO 自动生成的方法存根
		super.channelDisconnected(ctx, e);
	}

	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		System.out.println("=========channelClosed========11======");
		// TODO 自动生成的方法存根
		
		super.channelClosed(ctx, e);
	}
}
