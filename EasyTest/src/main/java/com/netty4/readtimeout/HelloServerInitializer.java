package com.netty4.readtimeout;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timer;

public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {
	/**
	 * ReadTimeoutHandler的实现思路：

开启一个定时任务，如果在指定时间内没有接收到消息，则抛出ReadTimeoutException

这个异常的捕获，在开发中，交给跟在ReadTimeoutHandler后面的ChannelHandler
	 */
	Timer trigger=new HashedWheelTimer();  
    final ChannelHandler timeOutHandler=new ReadTimeoutHandler(20);  
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // 以("\n")为结尾分割的 解码器
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));

        // 字符串解码 和 编码
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());
        pipeline.addLast("timeout", timeOutHandler);
        // 自己的逻辑Handler
        pipeline.addLast("handler", new HelloServerHandler());
    }
}