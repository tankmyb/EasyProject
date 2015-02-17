package com.easy.kit.logger;

public class DefaultErrorLogger extends AbstractLogger implements IDefaultErrorLogger{

	private final static String LOGGER_NAME = "DefaultErrorLogger";

	@Override
	protected String getLoggerName() {
		return LOGGER_NAME;
	}

	@Override
	public void error(StackTraceElement s, String msg) {
		logger.error("c:{},m:{},l:{},msg:{}",new Object[]{s.getClassName(),s.getMethodName(),s.getLineNumber()+"", msg});		
	}

	@Override
	public void error(String msg) {
		logger.error("msg:{} ", msg);
		
	}

	
}
