package com.netty.selectsend;

import java.util.concurrent.ConcurrentHashMap;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandler.Sharable;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.group.ChannelGroup;

@Sharable
public class MessageServerHandler extends SimpleChannelUpstreamHandler {
	public static ConcurrentHashMap<String, Channel> channels = new ConcurrentHashMap<String, Channel>();
	private final ChannelFutureListener remover = new ChannelFutureListener() {
        public void operationComplete(ChannelFuture future) throws Exception {
            remove(future.getChannel());
        }
    };

    @Override
    public void messageReceived(
            ChannelHandlerContext ctx, MessageEvent e) throws InterruptedException {
        if (!(e.getMessage() instanceof String)) {
            return;//(1)
        }
        String msg = (String) e.getMessage();
        if(msg.startsWith("ipport:")){
        	System.out.println(msg.substring(7,msg.length())+"=====sub");
        	boolean added = channels.putIfAbsent(msg.substring(7,msg.length()), e.getChannel()) == null;
            if (added) {
            	e.getChannel().getCloseFuture().addListener(remover);
            }
            return;
        }
        String id=msg.split(",")[0];
        System.out.println(id+"======="+e.getChannel().getId());
        System.out.println(channels.get(id));
        //System.err.println(new Date()+"===got msg:"+msg+"==="+Thread.currentThread().getName());
        channels.get(id).write("return:"+msg);//(2)
    }
 
    @Override
    public void exceptionCaught(
            ChannelHandlerContext ctx, ExceptionEvent e) {
        e.getChannel().close();
    }
    @Override
	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
    	System.out.println(e.getChannel()+"==="+e.getChannel().getId());
		//allChannelGroup.add(e.getChannel());
		System.out.println(e.getChannel().getRemoteAddress());
		super.channelOpen(ctx, e);
	}
    @Override
    public void channelConnected(
            ChannelHandlerContext ctx, ChannelStateEvent e) {
    	
    }
    public boolean remove(Object key) {
        Channel c = channels.get(key);
        if (c == null) {
            return false;
        }
        c.getCloseFuture().removeListener(remover);
        return true;
    }
}
