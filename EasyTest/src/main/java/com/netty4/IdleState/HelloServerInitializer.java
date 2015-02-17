package com.netty4.IdleState;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {
	/**
	 * ReadTimeoutHandler的实现思路：

开启一个定时任务，如果在指定时间内没有接收到消息，则抛出ReadTimeoutException

这个异常的捕获，在开发中，交给跟在ReadTimeoutHandler后面的ChannelHandler
	 */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // 以("\n")为结尾分割的 解码器
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));

        // 字符串解码 和 编码
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());
        pipeline.addLast("timeout", new IdleStateHandler(30,0,0));// 心跳控制
        // 自己的逻辑Handler
        pipeline.addLast("handler", new HelloServerHandler());
    }
}