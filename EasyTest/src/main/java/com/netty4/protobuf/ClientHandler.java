package com.netty4.protobuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.netty.protobuf.CarInfos.Car.CarInfo;

public class ClientHandler extends SimpleChannelInboundHandler<CarInfo> {

@Override
protected void channelRead0(ChannelHandlerContext ctx, CarInfo msg) throws Exception {
System.out.println("client:" + "channelRead:" + msg.getPrice());
}
}