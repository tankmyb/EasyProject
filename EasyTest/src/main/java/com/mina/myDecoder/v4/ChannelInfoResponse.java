package com.mina.myDecoder.v4;

import java.nio.charset.Charset;

import org.apache.log4j.Logger;

public class ChannelInfoResponse extends AbstrMessage {
	private Logger logger = Logger.getLogger(ChannelInfoResponse.class);

	private String ChannelName;

	private EventDto[] events;

	@Override
	public short getTag() {
		return (short) 0x8001;
	}

	@Override
	public int getLen(Charset charset) {
		int len = 4 + 1 + 2;
		try {
			if (events != null && events.length > 0) {
				for (int i = 0; i < events.length; i++) {
					EventDto edt = events[i];
					len += 1 + 4 + 1 + 4 + 2 + 1 + 4 + 1 + edt.getLen(charset);
				}
			}
			if (ChannelName != null && !"".equals(ChannelName)) {
				len += ChannelName.getBytes(charset).length;
			}
		} catch (Exception e) {
			logger.error("频道信息转换为字节码错误...", e);
		}
		return len;
	}

	@Override
	public int getDataOffset() {
		int len = 2 + 4 + 4 + 1 + 2;
		if (events != null && events.length > 0) {
			len += events.length * (1 + 4 + 1 + 4 + 2 + 1 + 4 + 1);
		}
		return len;
	}

	public String getChannelName() {
		return ChannelName;
	}

	public void setChannelName(String channelName) {
		ChannelName = channelName;
	}

	public EventDto[] getEvents() {
		return events;
	}

	public void setEvents(EventDto[] events) {
		this.events = events;
	}


}
