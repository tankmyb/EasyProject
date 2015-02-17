package com.easy.kit.utils.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorLogger {
	private static final Logger logger = LoggerFactory
			.getLogger("DefaultErrorLogger");

	public static void error(String msg) {
		logger.error("msg:{} ", msg);
	}

	public static void error(StackTraceElement s, String msg) {
		logger.error("c:{},m:{},l:{},msg:{}", new Object[] {
				s.getClassName(), s.getMethodName(),
				s.getLineNumber() + "", msg });
	}

}
