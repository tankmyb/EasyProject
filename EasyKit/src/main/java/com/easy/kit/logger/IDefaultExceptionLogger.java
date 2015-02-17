package com.easy.kit.logger;

/**
 * 
 * 默认exception日志输出
 *
 */
public interface IDefaultExceptionLogger {

	void exception(String className,String msg);

	void exception(Throwable t);
}
