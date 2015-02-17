package com.netty.readtimeout;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.timeout.ReadTimeoutHandler;
import org.jboss.netty.handler.timeout.WriteTimeoutHandler;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;

public class MyPipelineFactory implements ChannelPipelineFactory {
	private ChannelHandler serverHandler;

	public MyPipelineFactory(ChannelHandler serverHander) {
		this.serverHandler = serverHander;
	}

	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();
		Timer timer = new HashedWheelTimer();
		//如果设置了ReadTimeoutHandler这个Handler，且间隔10S未读数据，则会抛出一个ReadTimeoutException，默认情况下不会影响数据发送。
		//pipeline.addLast("timeout", new ReadTimeoutHandler(timer, 10));
		//pipeline.addLast("wtimeout", new WriteTimeoutHandler(timer, 10));
		pipeline.addLast("idleHandler", serverHandler);
		return pipeline;
	}

}
