package com.easy.kit.logger.conf;

public interface Lifecycle {
	/**
	 * 
	 * 	 用{@link PostConstruct @PostConstruct}批注修饰则不用在XML文件中配置
	 *
	 */
	public void initializing();
	/**
	 * 
	 * 	用{@link PreDestroy @PreDestroy}批注修饰则不用在XML文件中配置
	 *
	 */
	public void cleanup();
}
