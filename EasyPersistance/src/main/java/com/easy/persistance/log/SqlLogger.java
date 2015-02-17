package com.easy.persistance.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easy.persistance.common.TimeMeter;

public class SqlLogger {
	private static final Logger logger = LoggerFactory.getLogger("SqlLogger");
	 

	
	public static void sql(TimeMeter timeMeter, String sql) {
		logger.info("aa{},sql:{}", timeMeter.toString(),sql);
	}

	public static void sql(String sql) {
		logger.info("sql:{}", sql);
	}

	
}
