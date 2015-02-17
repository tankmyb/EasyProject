package com.netty4.erlproto;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.netty4.erlproto.EchoBuf.Echo;
import com.netty4.erlproto.HeaderBuf.Header;
import com.netty4.erlproto.MessageBuf.Message;

public class ServerHandler extends SimpleChannelInboundHandler<Message> {
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
	public void channelRead0(ChannelHandlerContext ctx, Message msg)
			throws Exception {
		System.out.println("========================");
		System.out.println("sdserver:" + "channelRead:" + msg.getHeader().getMtype());
		System.out.println(msg.getBody());
        Echo h = Echo.parseFrom(msg.getBody());
        System.out.println(h.getContent()+"================"+h.getValue());
		// long size = carInfoReq.getSerializedSize();
		// byte[] buf = carInfoReq.toByteArray();
        Message.Builder m = Message.newBuilder();
        m.setHeader(msg.getHeader());
        
        Echo.Builder e= EchoBuf.Echo.newBuilder();
        e.setContent("dssds极从");
        e.setValue(58);
        m.setBody(e.build().toByteString());
		 ctx.writeAndFlush(m.build());
	}
}