package com.mina.myDecoder.v4;

import java.nio.charset.Charset;

public class ChannelInfoRequest extends AbstrMessage {

	private String channel_desc;

	private int channel_id;

	@Override
	public short getTag() {
		return (short) 0x0001;
	}

	@Override
	public int getLen(Charset charset) {
		int len = 2 + 1;
		try {
			if (channel_desc != null && !"".equals(channel_desc)) {
				len += channel_desc.getBytes(charset).length;
			}
		} catch (Exception e) {
			System.err.println("频道说明转换为字节码错误...");
		}
		return len;
	}

	@Override
	public int getDataOffset() {
		int len = 2 + 4 + 2 + 1;
		return len;
	}

	public String getChannel_desc() {
		return channel_desc;
	}

	public void setChannel_desc(String channel_desc) {
		this.channel_desc = channel_desc;
	}

	public int getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(int channel_id) {
		this.channel_id = channel_id;
	}
}
