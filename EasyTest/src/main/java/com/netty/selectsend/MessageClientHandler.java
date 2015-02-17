package com.netty.selectsend;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.jboss.netty.channel.ChannelHandler.Sharable;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

@Sharable
public class MessageClientHandler extends SimpleChannelUpstreamHandler {
 
	 public String ipport;
	 public MessageClientHandler(String ipport){
		 this.ipport = ipport;
	 }
	
	@Override
	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
    	System.out.println(e.getChannel()+"==="+e.getChannel().getId());
	}
    @Override
    public void channelConnected(
            ChannelHandlerContext ctx, ChannelStateEvent e) {
        String message = "ipport:"+ipport;
        e.getChannel().write(message);
    }
 
    @Override
    public void messageReceived(
            ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        // Send back the received message to the remote peer.
       // System.err.println("messageReceived send message "+e.getMessage()+"=="+new Date());
    	String m = (String)e.getMessage();
    	System.out.println(m);
        super.messageReceived(ctx, e);
    }
 
    @Override
    public void exceptionCaught(
            ChannelHandlerContext ctx, ExceptionEvent e) {
        // Close the connection when an exception is raised.
        e.getChannel().close();
    }
}
