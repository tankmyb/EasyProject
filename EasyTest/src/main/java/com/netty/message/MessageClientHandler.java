package com.netty.message;

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
 
	public final static AtomicInteger index  =   new  AtomicInteger( 0 );
	 
	
 
    @Override
    public void channelConnected(
            ChannelHandlerContext ctx, ChannelStateEvent e) {
        //String message = "hello kafka0102";
        //e.getChannel().write(message);
    }
 
    @Override
    public void messageReceived(
            ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        // Send back the received message to the remote peer.
       // System.err.println("messageReceived send message "+e.getMessage()+"=="+new Date());
    	String m = (String)e.getMessage();
    	//if(index.incrementAndGet()%1000==0){
   	   	 System.out.println(index+"==="+new Date()+m);
   	    //}
        super.messageReceived(ctx, e);
    }
 
    @Override
    public void exceptionCaught(
            ChannelHandlerContext ctx, ExceptionEvent e) {
        // Close the connection when an exception is raised.
        e.getChannel().close();
    }
}
