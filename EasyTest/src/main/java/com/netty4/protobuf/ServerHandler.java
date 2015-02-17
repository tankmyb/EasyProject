package com.netty4.protobuf;
import com.netty.protobuf.CarInfos.Car.CarInfo;
import com.netty.protobuf.CarInfos.Car.CarModel;
import com.netty.protobuf.CarInfos.Car.CarOwner;
import com.netty.protobuf.CarInfos.Car.Sex;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandler extends SimpleChannelInboundHandler<CarInfo> {
@Override
public void channelRead0(ChannelHandlerContext ctx, CarInfo msg) throws Exception {
System.out.println("server:" + "channelRead:" + msg.getPrice());


//long size = carInfoReq.getSerializedSize();
//byte[] buf = carInfoReq.toByteArray();
//ctx.writeAndFlush(carInfo.build());
}
}