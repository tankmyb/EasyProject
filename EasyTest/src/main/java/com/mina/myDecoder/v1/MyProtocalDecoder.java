package com.mina.myDecoder.v1;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;

public class MyProtocalDecoder  implements MessageDecoder {  
    private final Charset charset;  
    public MyProtocalDecoder() {  
        this(Charset.defaultCharset());  
    }  
    public MyProtocalDecoder(Charset charset) {  
        this.charset = charset;  
    }  

 // 检查给定的IoBuffer是否适合解码
 	public MessageDecoderResult decodable(IoSession session, IoBuffer in) {
 	// 真实数据长度
 			int len = in.getInt();
 			if (in.remaining() < len) {
 				return MessageDecoderResult.NEED_DATA;
 			}
 			return MessageDecoderResult.OK;
 	}
 	public MessageDecoderResult decode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {
        CharsetDecoder decoder = charset.newDecoder();
		int len = in.getInt(); // tag
		byte flag = in.get(); // len
        System.err.println(len);
		byte[] temp = new byte[len];
		in.get(temp); // 数据区
		
		// ===============解析数据做准备======================
				IoBuffer buf = IoBuffer.allocate(100).setAutoExpand(true);
				buf.put(temp);
				buf.flip(); // 为获取基本数据区长度做准备


				String content = buf.getString(len, decoder);
        		MyProtocalPack pack = new MyProtocalPack((byte)1, content);
        		out.write(pack);
        		
        		return MessageDecoderResult.OK;  
    }  
    public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {  
    }  
    public void dispose(IoSession session) throws Exception {   
     
    }  


}
