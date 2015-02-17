package com.netty.message;

import static org.jboss.netty.channel.Channels.pipeline;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.handler.execution.ExecutionHandler;
import org.jboss.netty.handler.execution.MemoryAwareThreadPoolExecutor;
public class MessageServerPipelineFactory implements ChannelPipelineFactory {
	MessageDecoder d = new MessageDecoder();
	MessageEncoder e = new MessageEncoder();
	public ChannelPipeline getPipeline() throws Exception {
		
		ChannelPipeline pipeline = pipeline();

		 pipeline.addLast("decoder", d);
		 pipeline.addLast("encoder", e);
		 pipeline.addLast("handler", new MessageServerHandler());
		//return Channels.pipeline(new MessageDecoder(), new MessageEncoder(),
				//new MessageServerHandler());
		 return pipeline;
	}
}