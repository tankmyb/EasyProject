package com.netty.selectsend;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import static org.jboss.netty.channel.Channels.pipeline;

public class MessageClientPipelineFactory implements ChannelPipelineFactory {

	private String ipport;
	public MessageClientPipelineFactory(String ipport){
		this.ipport = ipport;
	}
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = pipeline();

		pipeline.addLast("decoder", new MessageDecoder());
		pipeline.addLast("encoder", new MessageEncoder());
		pipeline.addLast("handler", new MessageClientHandler(ipport));

		return pipeline;
	}
}