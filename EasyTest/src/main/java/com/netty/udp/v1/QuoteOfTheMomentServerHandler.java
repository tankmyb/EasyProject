package com.netty.udp.v1;

import java.util.Random;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ChildChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

public class QuoteOfTheMomentServerHandler extends SimpleChannelUpstreamHandler {  
  
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

	private static final Random random = new Random();  
  
    // Quotes from Mohandas K. Gandhi:  
    private static final String[] quotes = {  
        "Where there is love there is life.",  
        "First they ignore you, then they laugh at you, then they fight you, then you win.",  
        "Be the change you want to see in the world.",  
        "The weak can never forgive. Forgiveness is the attribute of the strong.",  
    };  
  
    private static String nextQuote() {  
        int quoteId;  
        synchronized (random) {  
            quoteId = random.nextInt(quotes.length);  
        }  
        return quotes[quoteId];  
    }  
  
    @Override  
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)  
            throws Exception {  
        String msg = (String) e.getMessage();  
        System.out.println(e.getRemoteAddress() + " request: " + msg);  
        if ("I want a QOTM".equals(msg)) {  
            e.getChannel().write("QOTM: " + nextQuote(),e.getRemoteAddress());  
        }  
    }  
  
    @Override  
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)  
            throws Exception {  
        e.getCause().printStackTrace();  
        // We don't close the channel because we can keep serving requests.  
    }  
}