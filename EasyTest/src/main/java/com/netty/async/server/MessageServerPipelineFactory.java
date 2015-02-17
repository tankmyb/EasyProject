package com.netty.async.server;

import static org.jboss.netty.channel.Channels.pipeline;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;
import org.jboss.netty.handler.execution.ExecutionHandler;
import org.jboss.netty.handler.execution.MemoryAwareThreadPoolExecutor;

import com.netty.async.ReqBean;

public class MessageServerPipelineFactory implements ChannelPipelineFactory {
	private final ExecutionHandler executionHandler = new ExecutionHandler(
	        new MemoryAwareThreadPoolExecutor(50, 1048576, 1048576));
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = pipeline();
		pipeline.addLast("encode", new ObjectEncoder());  
		pipeline.addLast("decode", new ObjectDecoder(ClassResolvers.cacheDisabled(ReqBean.class.getClassLoader())));
		pipeline.addLast("execution", executionHandler);
		 pipeline.addLast("handler", new MessageServerHandler());
		 return pipeline;
	}
}