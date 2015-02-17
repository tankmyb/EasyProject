package com.easy.kit.logger;

import com.easy.kit.utils.exception.ExceptionUtil;

public class DefaultExceptionLogger extends AbstractLogger implements IDefaultExceptionLogger{

	private final static String LOGGER_NAME = "DefaultExceptionLogger";

	@Override
	protected String getLoggerName() {
		return LOGGER_NAME;
	}

	@Override
	public void exception(String className, String msg) {
		logger.error("class:{},msg:{} ",className, msg);
		
	}

	@Override
	public void exception(Throwable t) {
		logger.error("{} ", ExceptionUtil.getDetailMessage(t));
	}

	
}
