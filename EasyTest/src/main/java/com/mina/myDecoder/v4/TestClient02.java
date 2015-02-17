package com.mina.myDecoder.v4;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class TestClient02 {
	private static Logger logger = Logger.getLogger(TestClient02.class);

	private static String HOST = "127.0.0.1";

	private static int PORT = 3005;

	public static void main(String[] args) {
		// 创建一个非阻塞的客户端程序
		IoConnector connector = new NioSocketConnector();
		// 设置链接超时时间
		connector.setConnectTimeout(30000);
		// 添加过滤器
		connector.getFilterChain().addLast(
				"codec",
				new ProtocolCodecFilter(new MyMessageCodecFactory(
						new MyMessageDecoder(Charset.forName("utf-8")),
						new MyMessageEncoder(Charset.forName("utf-8")))));
		// 添加业务逻辑处理器类
		connector.setHandler(new Demo2ClientHandler());
		IoSession session = null;
		try {
			ConnectFuture future = connector.connect(new InetSocketAddress(
					HOST, PORT));// 创建连接
			future.awaitUninterruptibly();// 等待连接创建完成
			session = future.getSession();// 获得session
			
			for(int i=0;i<3;i++){
				ChannelInfoRequest req = new ChannelInfoRequest(); // 发送请求
				req.setChannel_id(12345);
				req.setChannel_desc("mina在做测试哦哦....哇呀呀！！！"+i);
				session.write(req);// 发送消息
			}
			
		} catch (Exception e) {
			logger.error("客户端链接异常...", e);
		}

		session.getCloseFuture().awaitUninterruptibly();// 等待连接断开
		connector.dispose();
	}
}
