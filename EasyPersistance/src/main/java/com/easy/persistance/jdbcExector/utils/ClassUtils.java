package com.easy.persistance.jdbcExector.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类辅助
 * 
 * User: liyd Date: 2/12/14 Time: 10:08 PM
 */
public class ClassUtils {

	/** 日志对象 */
	private static final Logger LOG = LoggerFactory.getLogger(ClassUtils.class);

	/**
	 * Map keyed by class containing CachedIntrospectionResults. Needs to be a
	 * WeakHashMap with WeakReferences as values to allow for proper garbage
	 * collection in case of multiple class loaders.
	 */
	private static final Map<Class, BeanInfo> classCache = Collections
			.synchronizedMap(new WeakHashMap<Class, BeanInfo>());

	/**
	 * 获取类本身的BeanInfo，不包含父类属性
	 * 
	 * @param clazz
	 * @return
	 */
	public static BeanInfo getSelfBeanInfo(Class<?> clazz) {
		try {
			BeanInfo beanInfo;
			if (classCache.get(clazz) == null) {
				beanInfo = Introspector.getBeanInfo(clazz,
						clazz.getSuperclass());
				classCache.put(clazz, beanInfo);
				// Immediately remove class from Introspector cache, to allow
				// for proper
				// garbage collection on class loader shutdown - we cache it
				// here anyway,
				// in a GC-friendly manner. In contrast to
				// CachedIntrospectionResults,
				// Introspector does not use WeakReferences as values of its
				// WeakHashMap!
				Class classToFlush = clazz;
				do {
					Introspector.flushFromCaches(classToFlush);
					classToFlush = classToFlush.getSuperclass();
				} while (classToFlush != null);
			} else {
				beanInfo = classCache.get(clazz);
			}
			return beanInfo;
		} catch (IntrospectionException e) {
			LOG.error("获取BeanInfo失败", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 初始化实例
	 * 
	 * @param clazz
	 * @return
	 */
	public static Object newInstance(Class<?> clazz) {
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			LOG.error("根据class创建实例失败", e);
			throw new RuntimeException(e);
		}
	}
}