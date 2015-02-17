package com.mina.myDecoder.v4;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;

public class MyMessageDecoder  implements MessageDecoder {
	private Logger logger = Logger.getLogger(MyMessageDecoder.class);

	private Charset charset;

	public MyMessageDecoder(Charset charset) {
		this.charset = charset;
	}

	// 检查给定的IoBuffer是否适合解码
	public MessageDecoderResult decodable(IoSession session, IoBuffer in) {
		// 报头长度==6
		if (in.remaining() < 6) {
			return MessageDecoderResult.NEED_DATA;
		}

		// tag正常
		short tag = in.getShort();
		// 注意先把16进制标识值转换为short类型的十进制数据，然后与tag比较
		if (tag == (short) 0x0001 || tag == (short) 0x8001) {
			logger.info("请求标识符：" + tag);
		} else {
			logger.error("未知的解码类型....");
			return MessageDecoderResult.NOT_OK;
		}

		// 真实数据长度
		int len = in.getInt();
		if (in.remaining() < len) {
			return MessageDecoderResult.NEED_DATA;
		}

		return MessageDecoderResult.OK;
	}

	public MessageDecoderResult decode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		System.err.println("============");
		logger.info("解码：" + in.toString());
		CharsetDecoder decoder = charset.newDecoder();
		AbstrMessage message = null;
		short tag = in.getShort(); // tag
		int len = in.getInt(); // len

		byte[] temp = new byte[len];
		in.get(temp); // 数据区

		// ===============解析数据做准备======================
		IoBuffer buf = IoBuffer.allocate(100).setAutoExpand(true);
		buf.put(temp);
		buf.flip(); // 为获取基本数据区长度做准备

		IoBuffer databuf = IoBuffer.allocate(100).setAutoExpand(true);
		databuf.putShort(tag);
		databuf.putInt(len);
		databuf.put(temp);
		databuf.flip(); // 为获取真实数据区长度做准备

		// ================开始解码=========================
		// 注意先把16进制标识值转换为short类型的十进制数据，然后与tag比较
		if (tag == (short) 0x0001) { // 服务端解码
			ChannelInfoRequest req = new ChannelInfoRequest();

			short channel_id = buf.getShort();
			byte channel_desc_len = buf.get();
			String channel_desc = null;
			if (channel_desc_len > 0) {
				channel_desc = buf.getString(channel_desc_len, decoder);
			}

			req.setChannel_id(channel_id);
			req.setChannel_desc(channel_desc);

			message = req;
		} else if (tag == (short) 0x8001) { // 客户端解码
			ChannelInfoResponse res = new ChannelInfoResponse();

			int channel_addr = buf.getInt();
			byte channel_len = buf.get();
			if (databuf.position() == 0) {
				databuf.position(channel_addr);
			}
			String channelName = null;
			if (channel_len > 0) {
				channelName = databuf.getString(channel_len, decoder);
			}
			res.setChannelName(channelName);

			short event_num = buf.getShort();
			EventDto[] events = new EventDto[event_num];
			for (int i = 0; i < event_num; i++) {
				EventDto edt = new EventDto();
				byte dayIndex = buf.get();

				buf.getInt();
				byte eventName_len = buf.get();
				String eventName = null;
				if (eventName_len > 0) {
					eventName = databuf.getString(eventName_len, decoder);
				}

				int beginTime = buf.getInt();
				short totalTime = buf.getShort();
				byte status = buf.get();

				buf.getInt();
				byte url_len = buf.get();
				String url = null;
				if (url_len > 0) {
					url = databuf.getString(url_len, decoder);
				}
				edt.setDayIndex(dayIndex);
				edt.setEventName(eventName);
				edt.setBeginTime(beginTime);
				edt.setTotalTime(totalTime);
				edt.setStatus(status);
				edt.setUrl(url);

				events[i] = edt;
			}
			res.setEvents(events);
			message = res;
		} else {
			logger.error("未找到解码器....");
		}
		out.write(message);
		// ================解码成功=========================
		return MessageDecoderResult.OK;
	}

	public void finishDecode(IoSession session, ProtocolDecoderOutput out)
			throws Exception {
	}

}
