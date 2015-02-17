package com.mina.myDecoder.v1;

import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageEncoder;

public class MyProtocalCodecFactory extends DemuxingProtocolCodecFactory {  
    private MessageDecoder decoder;

	private MessageEncoder<MyProtocalPack> encoder;
	// 注册编解码器
	public MyProtocalCodecFactory(MessageDecoder decoder,
			MessageEncoder<MyProtocalPack> encoder) {
		this.decoder = decoder;
		this.encoder = encoder;
		addMessageDecoder(this.decoder);
		addMessageEncoder(MyProtocalPack.class, this.encoder);
	}
 
      

}
