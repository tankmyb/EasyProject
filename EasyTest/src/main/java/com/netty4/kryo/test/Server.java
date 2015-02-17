package com.netty4.kryo.test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import com.netty4.kryo.KryoDecoder;
import com.netty4.kryo.KryoEncoder;
import com.netty4.kryo.KryoPool;

public class Server {

	public static void main(String[] args) {
		final KryoPool p = new KryoPool();
		p.init();
		EventLoopGroup bossEventLoopGroup = new NioEventLoopGroup();
		EventLoopGroup workerEventLoopGroup = new NioEventLoopGroup(10);
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossEventLoopGroup, workerEventLoopGroup);
			serverBootstrap.channel(NioServerSocketChannel.class);
			serverBootstrap.childHandler(new ChannelInitializer<Channel>() {
				@Override
				protected void initChannel(Channel ch) throws Exception {
					ch.pipeline().addLast(new KryoEncoder(p));
					ch.pipeline().addLast(new KryoDecoder(p));
					ch.pipeline().addLast("handler", new ServerHandler());
				};
			});
			serverBootstrap.option(ChannelOption.SO_RCVBUF, 10000);
			serverBootstrap.option(ChannelOption.SO_SNDBUF, 10000);
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