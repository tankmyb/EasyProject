package com.easy.kit.logger;

/**
 * 
 * 默认error日志输出
 *
 */
public interface IDefaultErrorLogger {

	void error(StackTraceElement s,String msg);

	void error(String msg);
}
