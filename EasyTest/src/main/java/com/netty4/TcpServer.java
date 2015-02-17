package com.netty4;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.Date;

public class TcpServer {

	public static void main(String[] args) throws InterruptedException {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1); // 服务器监听一个端口号，boss线程数建议设置成1
		EventLoopGroup workerGroup = new NioEventLoopGroup(4); // worker线程数设置成4
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						
						// 创建一个16个线程的线程组来处理耗时的业务逻辑
						private EventExecutorGroup group = new DefaultEventExecutorGroup(16);
						
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline pipeline = ch.pipeline();
							pipeline.addLast(new LineBasedFrameDecoder(80));
							pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
							
							// 将TcpServerHandler中的业务逻辑放到EventExecutorGroup线程组中执行
							pipeline.addLast(group, new TcpServerHandler());
						}
					});
			ChannelFuture f = b.bind(7878).sync();
			f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

}

class TcpServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws InterruptedException {
		
		// 假设这里有个变态的SQL要执行3秒
		Thread.sleep(3000);
        System.out.println(new Date()+"======================"+Thread.currentThread().getName());
	}
}