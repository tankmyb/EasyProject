package com.mina.fileUpload.v1;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class FileSenderHandler extends IoHandlerAdapter {
	@Override
	public void messageReceived(IoSession session, Object message)
	throws Exception {
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
	throws Exception {
	session.close(true);
	}


}
