package com.netty.selectsend.v1;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.execution.ExecutionHandler;
import org.jboss.netty.handler.execution.OrderedMemoryAwareThreadPoolExecutor;
import static org.jboss.netty.channel.Channels.pipeline;
public class MessageServerPipelineFactory implements ChannelPipelineFactory {
	double coefficient = 0.8;  //系数
	int numberOfCores = Runtime.getRuntime().availableProcessors();
	int poolSize = (int)(numberOfCores / (1 - coefficient));
	private final ExecutionHandler executionHandler = new ExecutionHandler(
	        new OrderedMemoryAwareThreadPoolExecutor(numberOfCores, 1048576, 1048576));
	public ChannelPipeline getPipeline() throws Exception {
		
		ChannelPipeline pipeline = pipeline();

		 pipeline.addLast("decoder", new MessageDecoder());
		 pipeline.addLast("encoder", new MessageEncoder());
		 pipeline.addLast("execution", executionHandler);
		 pipeline.addLast("handler1", new TestServerHandler());
		 pipeline.addLast("handler", new MessageServerHandler());
		//return Channels.pipeline(new MessageDecoder(), new MessageEncoder(),
				//new MessageServerHandler());
		 return pipeline;
	}
}