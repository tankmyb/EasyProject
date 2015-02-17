package com.netty.selectsend.v1;

import java.util.concurrent.ConcurrentHashMap;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandler.Sharable;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.ServerChannel;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;

@Sharable
public class MessageServerHandler extends SimpleChannelUpstreamHandler {
 
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx,
			ChannelStateEvent e) throws Exception {
		System.out.println("=========channelDisconnected==============");
		System.out.println(e.getChannel());
		// TODO 自动生成的方法存根
		super.channelDisconnected(ctx, e);
	}

	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		System.out.println("=========channelClosed==============");
		// TODO 自动生成的方法存根
		
		remove(e.getChannel().getRemoteAddress().toString());
		super.channelClosed(ctx, e);
	}
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
        String id=msg.split(",")[0];
        System.out.println(id+"======="+e.getChannel().getId());
        System.out.println(channels.get(id));
        //System.err.println(new Date()+"===got msg:"+msg+"==="+Thread.currentThread().getName());
        channels.get(id).write("return:"+msg);//(2)
    }
 
    @Override
    public void exceptionCaught(
            ChannelHandlerContext ctx, ExceptionEvent e) {
    	System.out.println("==========exceptionCaught=================");
        e.getChannel().close();
    }
    @Override
	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
    	System.out.println(e.getChannel().getRemoteAddress().toString());
    	boolean added = channels.putIfAbsent(e.getChannel().getRemoteAddress().toString(), e.getChannel()) == null;
        if (added) {
        	e.getChannel().getCloseFuture().addListener(remover);
        }
		//allChannelGroup.add(e.getChannel());
		super.channelOpen(ctx, e);
	}
    @Override
    public void channelConnected(
            ChannelHandlerContext ctx, ChannelStateEvent e) {
    	System.out.println(e.getValue()+"===value");
    }
    public boolean remove(Object key) {
        Channel c = channels.get(key);
        if (c == null) {
            return false;
        }
        c.getCloseFuture().removeListener(remover);
        channels.remove(key);
        c.close();
        return true;
    }
}
