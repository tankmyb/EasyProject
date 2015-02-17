package com.netty4.IdleState;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.net.InetAddress;

public class HelloServerHandler extends SimpleChannelInboundHandler<String> {
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // 收到消息直接打印输出
        System.out.println(ctx.channel().remoteAddress() + " Say : " + msg);
        
        // 返回客户端消息 - 我已经接收到了你的消息
        ctx.writeAndFlush("Received your message !\n");
    }
    
    @Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
    	cause.printStackTrace();
		super.exceptionCaught(ctx, cause);
	}

	/*
     * 
     * 覆盖 channelActive 方法 在channel被启用的时候触发 (在建立连接的时候)
     * 
     * channelActive 和 channelInActive 在后面的内容中讲述，这里先不做详细的描述
     * */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        
        System.out.println("RamoteAddress : " + ctx.channel().remoteAddress() + " active !");
        
        ctx.writeAndFlush( "Welcome to " + InetAddress.getLocalHost().getHostName() + " service!\n");
        
        super.channelActive(ctx);
    }
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
            throws Exception {

        /*心跳处理*/
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                /*读超时*/
                System.out.println("READER_IDLE 读超时");
                ctx.disconnect();
            } else if (event.state() == IdleState.WRITER_IDLE) {
                /*写超时*/   
                System.out.println("WRITER_IDLE 写超时");
            } else if (event.state() == IdleState.ALL_IDLE) {
                /*总超时*/
                System.out.println("ALL_IDLE 总超时");
            }
        }
    }
}