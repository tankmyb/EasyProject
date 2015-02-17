package com.netty4.erlproto;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;

public class Server {

	public static void main(String[] args) {
		EventLoopGroup bossEventLoopGroup = new NioEventLoopGroup();
		EventLoopGroup workerEventLoopGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossEventLoopGroup, workerEventLoopGroup);
			serverBootstrap.channel(NioServerSocketChannel.class);
			serverBootstrap.childHandler(new ChannelInitializer<Channel>() {
				@Override
				protected void initChannel(Channel ch) throws Exception {
					// ch.pipeline().addLast("frameEncoder", new
					// LengthFieldPrepender(4));
					ch.pipeline().addLast("frameEncoder",
							new LengthFieldPrepender(4));
					ch.pipeline().addLast("encoder", new ProtobufEncoder());
					// ch.pipeline().addLast("frameDecoder", new
					// LengthFieldBasedFrameDecoder(1048576, 0, 4, 0, 4));

					ch.pipeline().addLast("frameDecoder",
							new ProtobufVarint32FrameDecoder());
					ch.pipeline().addLast(
							"decoder",
							new ProtobufDecoder(MessageBuf.Message
									.getDefaultInstance()));
					ch.pipeline().addLast("handler", new ServerHandler());
				};
			});
			serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
			ChannelFuture channelFuture = serverBootstrap.bind(8888).sync();
			channelFuture.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bossEventLoopGroup.shutdownGracefully();
			workerEventLoopGroup.shutdownGracefully();
		}
	}
}