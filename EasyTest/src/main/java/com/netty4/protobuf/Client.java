package com.netty4.protobuf;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.netty.protobuf.CarInfos.Car.CarInfo;
import com.netty.protobuf.CarInfos.Car.CarModel;
import com.netty.protobuf.CarInfos.Car.CarOwner;
import com.netty.protobuf.CarInfos.Car.Sex;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class Client {

public static void main(String[] args) {
EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
try {
Bootstrap bootstrap = new Bootstrap();
bootstrap.group(eventLoopGroup);
bootstrap.channel(NioSocketChannel.class);
bootstrap.handler(new ChannelInitializer<Channel>() {
@Override
protected void initChannel(Channel ch) throws Exception {
	//ch.pipeline().addLast("frameEncoder", new LengthFieldPrepender(4));
		ch.pipeline().addLast("frameEncoder",new ProtobufVarint32LengthFieldPrepender());
	ch.pipeline().addLast("encoder", new ProtobufEncoder());
	//ch.pipeline().addLast("frameDecoder",  new LengthFieldBasedFrameDecoder(1048576, 0, 4, 0, 4));



	ch.pipeline().addLast("frameDecoder", new ProtobufVarint32FrameDecoder());
ch.pipeline().addLast("decoder", new ProtobufDecoder(CarInfo.getDefaultInstance()));
ch.pipeline().addLast("handler", new ClientHandler());
};
});

Channel ch = bootstrap.connect("127.0.0.1", 8888).sync().channel();

// 控制台输入
BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
for (;;) {
String line = in.readLine();
if (line == null || "".equals(line)) {
continue;
}
CarOwner.Builder owner = CarOwner.newBuilder();
owner.setAge(18);
owner.setHeight(170);
owner.setName("jimmy");
owner.setSex(Sex.FEMALE);
CarInfo.Builder carInfo = CarInfo.newBuilder();
carInfo.setBrand("大众");
carInfo.setCarNumber(line);
carInfo.setColor("red");
carInfo.setModel(CarModel.VOLKSWAGEN);
carInfo.setPrice(1000);
carInfo.setOwner(owner);
ch.writeAndFlush(carInfo.build());
}
} catch (Exception e) {
e.printStackTrace();
} finally {
eventLoopGroup.shutdownGracefully();
}
}
}