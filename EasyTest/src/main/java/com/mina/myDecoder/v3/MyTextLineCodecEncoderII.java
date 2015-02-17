package com.mina.myDecoder.v3;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class MyTextLineCodecEncoderII implements ProtocolEncoder {
	private Charset charset; // 编码格式

	private String delimiter; // 文本分隔符

	public MyTextLineCodecEncoderII(Charset charset, String delimiter) {
		this.charset = charset;
		this.delimiter = delimiter;
	}

	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		if (delimiter == null || "".equals(delimiter)) { // 如果文本换行符未指定，使用默认值
			delimiter = "\r\n";
		}
		if (charset == null) {
			charset = Charset.forName("utf-8");
		}

		String value = message.toString();
		IoBuffer buf = IoBuffer.allocate(value.length()).setAutoExpand(true);
		buf.putString(value, charset.newEncoder()); // 真实数据
		buf.putString(delimiter, charset.newEncoder()); // 文本换行符
		buf.flip();
		out.write(buf);
	}

	public void dispose(IoSession session) throws Exception {
	}

}
