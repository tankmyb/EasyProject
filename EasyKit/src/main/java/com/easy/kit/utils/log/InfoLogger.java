package com.easy.kit.utils.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InfoLogger {
	 private static final Logger logger = LoggerFactory.getLogger("DefaultInfoLogger");
	 
	 public static void info(String className,String msg) {
		
		 logger.info("class:{},msg:{} ",className, msg);
	 }
	 public static void info(String msg) {
		 System.out.println(InfoLogger.class);
		 logger.info("{} ", msg);
	 }
}
