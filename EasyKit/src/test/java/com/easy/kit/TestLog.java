package com.easy.kit;

import javax.annotation.Resource;

import org.junit.Test;

import com.easy.kit.logger.IDefaultErrorLogger;
import com.easy.kit.logger.IDefaultExceptionLogger;
import com.easy.kit.logger.IDefaultInfoLogger;
import com.easy.kit.utils.log.ErrorLogger;
import com.easy.kit.utils.log.ExceptionLogger;
import com.easy.kit.utils.log.InfoLogger;


public class TestLog extends BaseTest{

	@Resource 
	private IDefaultInfoLogger infoLogger;
	@Resource 
	private IDefaultErrorLogger errorLogger;
	@Resource 
	private IDefaultExceptionLogger exceptionLogger;
	@Test
	public  void log() {
		ExceptionLogger.exception("a", "aaaaaa");
		ErrorLogger.error("aaaaaaaaaaaa");
		InfoLogger.info("aaaaaaaaaaaaaaaaaaaainfo111111111");
	}
	@Test
	public  void log1() {
		infoLogger.info("aaaaaaaaaaaaaaaaaaa");
		errorLogger.error("error");
		exceptionLogger.exception("aaa", "aaaaa");
	}

	
}
