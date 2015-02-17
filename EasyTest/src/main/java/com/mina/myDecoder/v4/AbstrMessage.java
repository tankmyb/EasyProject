package com.mina.myDecoder.v4;

import java.nio.charset.Charset;

public abstract  class AbstrMessage {
	// 协议编号
		public abstract short getTag();

		// 数据区长度
		public abstract int getLen(Charset charset);

		// 真实数据偏移地址
		public abstract int getDataOffset();
}
