package com.netty4.erlproto.str;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.netty4.erlproto.EchoBuf.Echo;

public class ClientHandler extends SimpleChannelInboundHandler<String> {

@Override
protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
System.out.println("client:" + "channelRead:" + msg);
}
}