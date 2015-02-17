package com.mina.myDecoder.v4;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class Demo2ClientHandler extends IoHandlerAdapter {
	private static Logger logger = Logger.getLogger(Demo2ClientHandler.class);

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		if (message instanceof ChannelInfoResponse) {
			ChannelInfoResponse res = (ChannelInfoResponse) message;
			String channelName = res.getChannelName();
			EventDto[] events = res.getEvents();
			logger.info("客户端接收到的消息为：channelName=" + channelName);
			if(events!=null && events.length>0){
				for (int i = 0; i < events.length; i++) {
					EventDto edt = events[i];
					logger.info("客户端接收到的消息为：BeginTime=" + edt.getBeginTime());
					logger.info("客户端接收到的消息为：DayIndex=" + edt.getDayIndex());
					logger.info("客户端接收到的消息为：EventName=" + edt.getEventName());
					logger.info("客户端接收到的消息为：Status=" + edt.getStatus());
					logger.info("客户端接收到的消息为：TotalTime=" + edt.getTotalTime());
					logger.info("客户端接收到的消息为：url=" + edt.getUrl());
				}
			}
		}else{
			logger.info("未知类型！");
		}
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		logger.error("客户端发生异常...", cause);
	}

}
