package com.mina.v1;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class TimeServer {

	static Log log = LogFactory.getLog(TimeServer.class);
	public static void main(String[] args) {
		final IoAcceptor acceptor = new NioSocketAcceptor();
		acceptor.setHandler(new IoHandlerAdapter(){
			@Override
			public void messageSent(final IoSession session,final Object message) throws Exception{
				//session.close(false);
			}
			@Override
			public void sessionOpened(final IoSession session) throws Exception{
				
			}
			@Override
			public void messageReceived( IoSession session, Object message ) throws Exception{
				//System.out.println(message);
				System.out.println("============"+((String)message).length());
				//TestBean bean = new TestBean();
				//bean.setName("sdsdsdsdsdsdsdsdsdsd");
				
				//Map bean = new HashMap();
				//bean.put("name", "1111111111");
				//session.write(bean);
			}
		});
		acceptor.getFilterChain().addLast("myChin", new ProtocolCodecFilter(  
        new ObjectSerializationCodecFactory()));  
		//acceptor.getFilterChain().addLast("myText", new ProtocolCodecFilter(new TextLineCodecFactory()));
		//acceptor.getFilterChain().addLast("logging", new LoggingFilter());
		try{
			acceptor.bind(new InetSocketAddress(12121));
			log.info("bind Mina server");
		}catch (Exception e) {
			log.error("error");
		}
	}
}
