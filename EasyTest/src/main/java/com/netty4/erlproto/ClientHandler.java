package com.netty4.erlproto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.netty4.erlproto.EchoBuf.Echo;

public class ClientHandler extends SimpleChannelInboundHandler<Echo> {

@Override
protected void channelRead0(ChannelHandlerContext ctx, Echo msg) throws Exception {
System.out.println("client:" + "channelRead:" + msg.getContent());
}
}