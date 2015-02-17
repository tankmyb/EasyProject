package com.netty.udp.v1;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ChildChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

public class QuoteOfTheMomentClientHandler extends SimpleChannelUpstreamHandler {  

    @Override
	public void channelDisconnected(ChannelHandlerContext ctx,
			ChannelStateEvent e) throws Exception {
    	System.out.println("=========channelDisconnected=====");
		// TODO 自动生成的方法存根
		super.channelDisconnected(ctx, e);
	}

	@Override
	public void childChannelClosed(ChannelHandlerContext ctx,
			ChildChannelStateEvent e) throws Exception {
		System.out.println("=========childChannelClosed=====");
		super.childChannelClosed(ctx, e);
	}
    @Override  
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)  
            throws Exception {  
        String msg = (String) e.getMessage();  
        if (msg.startsWith("QOTM: ")) {  
            System.out.println("Quote of the Moment: " + msg.substring(6) + " from " + e.getRemoteAddress());  
            e.getChannel().close();  
        }  
    }  
  
    @Override  
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)  
            throws Exception {  
        e.getCause().printStackTrace();  
        e.getChannel().close();  
    }  
}  