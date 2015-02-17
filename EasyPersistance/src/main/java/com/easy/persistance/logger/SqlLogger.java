package com.easy.persistance.logger;

import com.easy.kit.logger.AbstractLogger;
import com.easy.persistance.common.TimeMeter;

public class SqlLogger extends AbstractLogger implements ISqlLogger{

	private final static String LOGGER_NAME = "SqlLogger";

	@Override
	protected String getLoggerName() {
		return LOGGER_NAME;
	}

	@Override
	public void sql(TimeMeter timeMeter, String sql) {
		logger.info("{},sql:{}", timeMeter.toString(),sql);
	}

	@Override
	public void sql(String sql) {
		logger.info("sql:{}", sql);
	}

	
}
