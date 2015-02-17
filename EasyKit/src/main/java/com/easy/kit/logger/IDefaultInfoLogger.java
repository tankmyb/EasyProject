package com.easy.kit.logger;

/**
 * 
 * 默认info日志输出
 *
 */
public interface IDefaultInfoLogger {

	void info(String className, String msg);

	void info(String msg);
}
