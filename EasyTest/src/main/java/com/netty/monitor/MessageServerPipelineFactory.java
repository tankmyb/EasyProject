package com.netty.monitor;

import static org.jboss.netty.channel.Channels.pipeline;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.handler.execution.ExecutionHandler;
import org.jboss.netty.handler.execution.MemoryAwareThreadPoolExecutor;
public class MessageServerPipelineFactory implements ChannelPipelineFactory {
	private MemoryAwareThreadPoolExecutor executor;
	ExecutionHandler executionHandler;
	public MessageServerPipelineFactory(MemoryAwareThreadPoolExecutor executor){
		this.executor = executor;
		executionHandler = new ExecutionHandler(executor);
	}
	public ChannelPipeline getPipeline() throws Exception {
		
		ChannelPipeline pipeline = pipeline();

		 pipeline.addLast("decoder", new MessageDecoder());
		 pipeline.addLast("encoder", new MessageEncoder());
		 pipeline.addLast("execution", executionHandler);
		 pipeline.addLast("handler", new MessageServerHandler(executor));
		//return Channels.pipeline(new MessageDecoder(), new MessageEncoder(),
				//new MessageServerHandler());
		 return pipeline;
	}
}