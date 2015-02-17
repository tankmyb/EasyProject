package com.mina.myDecoder.v4;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

public class MyMessageEncoder implements MessageEncoder<AbstrMessage> {
	private Logger logger = Logger.getLogger(MyMessageEncoder.class);

	private Charset charset;

	public MyMessageEncoder(Charset charset) {
		this.charset = charset;
	}

	public void encode(IoSession session, AbstrMessage message,
			ProtocolEncoderOutput out) throws Exception {
		IoBuffer buf = IoBuffer.allocate(100).setAutoExpand(true);
		buf.putShort(message.getTag());
		buf.putInt(message.getLen(charset));

		// ===========编码数据区===============
		if (message instanceof ChannelInfoRequest) {
			ChannelInfoRequest req = (ChannelInfoRequest) message;
			buf.putShort((short) req.getChannel_id());
			buf.put((byte) req.getChannel_desc().getBytes(charset).length);
			buf.putString(req.getChannel_desc(), charset.newEncoder());
		} else if (message instanceof ChannelInfoResponse) {
			ChannelInfoResponse res = (ChannelInfoResponse) message;
			CharsetEncoder encoder = charset.newEncoder();
			IoBuffer dataBuffer = IoBuffer.allocate(100).setAutoExpand(true); // 定义真实数据区
			int offset = res.getDataOffset(); // 偏移地址

			buf.putInt(offset); // 频道名称地址（偏移开始位置）
			byte channelName_len = 0;
			if (res.getChannelName() != null) {
				channelName_len = (byte) res.getChannelName().getBytes(charset).length;
			}
			buf.put(channelName_len);
			offset += channelName_len;
			if (channelName_len > 0) {
				dataBuffer.putString(res.getChannelName(), encoder);
			}

			EventDto[] events = res.getEvents();
			if (events != null) {
				buf.putShort((short) events.length);
				for (int i = 0; i < events.length; i++) {
					EventDto edt = events[i];

					buf.put((byte) edt.getDayIndex());

					buf.putInt(offset);
					String eventName = edt.getEventName();
					byte eventName_len = 0;
					if (eventName != null) {
						eventName_len = (byte) eventName.getBytes(charset).length;
					}
					offset += eventName_len;
					buf.put(eventName_len);
					if (eventName_len > 0) {
						dataBuffer.putString(eventName, encoder);
					}

					buf.putInt(edt.getBeginTime());
					buf.putShort((short) edt.getTotalTime());
					buf.put((byte) edt.getStatus());

					buf.putInt(offset);
					String url = edt.getUrl();
					byte url_len = 0;
					if (url != null) {
						url_len = (byte) url.getBytes(charset).length;
					}
					offset += url_len;
					buf.put(url_len);
					if (url_len > 0) {
						dataBuffer.putString(url, encoder);
					}
				}
			}

			// 真实数据追加在基本数据后面
			if (dataBuffer.position() > 0) {
				buf.put(dataBuffer.flip());
			}
		}

		// ==========编码成功=================
		buf.flip();
		logger.info("编码" + buf.toString());
		out.write(buf);
	}

}
