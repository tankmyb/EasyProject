package com.easy.kit.logger;

public class DefaultInfoLogger extends AbstractLogger implements IDefaultInfoLogger{

	private final static String LOGGER_NAME = "DefaultInfoLogger";

	@Override
	protected String getLoggerName() {
		return LOGGER_NAME;
	}

	@Override
	public void info(String className, String msg) {
		logger.info("class:{},msg:{} ",className, msg);
		
	}

	@Override
	public void info(String msg) {
		logger.info("{} ", msg);
	}
}
