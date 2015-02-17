package com.netty4.erlproto.str;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.netty4.erlproto.EchoBuf.Echo;

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
					ch.pipeline().addLast("encoder", new StringEncoder());

					ch.pipeline().addLast("decoder", new StringDecoder());
					ch.pipeline().addLast("handler", new ClientHandler());
				};
			});

			Channel ch = bootstrap.connect("127.0.0.1", 8888).sync().channel();

			// 控制台输入
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			for (;;) {
				String line = in.readLine();
				if (line == null || "".equals(line)) {
					continue;
				}
				ch.writeAndFlush(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			eventLoopGroup.shutdownGracefully();
		}
	}
}