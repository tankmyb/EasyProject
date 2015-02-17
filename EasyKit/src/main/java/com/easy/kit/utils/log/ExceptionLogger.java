package com.easy.kit.utils.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easy.kit.utils.exception.ExceptionUtil;

public class ExceptionLogger {
	 private static final Logger logger = LoggerFactory.getLogger("DefaultExceptionLogger");
	 
	 public static void exception(String className,String msg) {
		 logger.error("class:{},msg:{} ",className, msg);
	 }
	 public static void exception(Throwable t) {
		 logger.error("{} ", ExceptionUtil.getDetailMessage(t));
	 }
}
