package com.easy.kit.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.easy.kit.utils.assertutil.AssertUtil;
public abstract class AbstractLogger {

	protected Logger logger;
	public AbstractLogger() {
		this.initializing();
	}
	protected void initializing() {
		String loggerName = this.getLoggerName();
		AssertUtil.notBlank(loggerName, "The logger name not should be empty!");
		logger = LoggerFactory.getLogger(loggerName);
	}
	protected abstract String getLoggerName();
}
