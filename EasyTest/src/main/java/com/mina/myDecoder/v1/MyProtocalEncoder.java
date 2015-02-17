package com.mina.myDecoder.v1;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

public class MyProtocalEncoder implements MessageEncoder<MyProtocalPack> {  
    private final Charset charset;  
    public MyProtocalEncoder(Charset charset) {  
        this.charset = charset;  
    }  
    //在此处实现对MyProtocalPack包的编码工作，并把它写入输出流中  
    public void encode(IoSession session, MyProtocalPack message,
			ProtocolEncoderOutput out) throws Exception {  
        MyProtocalPack value = (MyProtocalPack) message;  
        IoBuffer buf = IoBuffer.allocate(value.getLength());  
        buf.setAutoExpand(true);  
        buf.putInt(value.getLength());  
        buf.put(value.getFlag());  
        if (value.getContent() != null)  
            buf.put(value.getContent().getBytes());  
        buf.flip();  
        out.write(buf);  
    }  
    public void dispose() throws Exception {  
    }  

}
