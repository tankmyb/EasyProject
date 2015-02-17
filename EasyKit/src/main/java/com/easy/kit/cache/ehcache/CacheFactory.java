package com.easy.kit.cache.ehcache;

import java.net.URL;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class CacheFactory {

	private static CacheManager manager;
	private static Cache cache;
	static {
		CacheFactory cs = new CacheFactory();
		cs.init();
	}

	/**
	 * 
	 * 初试化cache
	 */

	private void init() {
		URL url = getClass().getResource("/ehcache.xml");
		manager = CacheManager.create(url);
		System.out.println(url);
		cache = manager.getCache("UrlCache");
	}

	/**
	 * 
	 * 清除cache
	 */

	@SuppressWarnings("unused")
	private void destory() {
		manager.shutdown();
	}

	/**
	 * 
	 * 得到某个key的cache值
	 * @param key
	 * 
	 * @return
	 */

	public static Object get(String key) {
        Element element = cache.get(key);
        if(element != null){
        	return element.getObjectValue();
        }
		return null;

	}
	/**
	 * 清楚key的cache
	 * @param key
	 */

	public static void remove(String key) {
		cache.remove(key);
	}

	/**
	 * 
	 * 设置或更新某个cache值
	 * @param element
	 */

	public static void put(String key,Object value) {
		Element element = new Element(key, value);
		cache.put(element);
	}

	public static void removeAll() {
		cache.removeAll();
	}
	/**
	 * 得到在内存中的元素数量 
	 * @return
	 */
	public long getMemoryStoreSize(){
		return cache.getMemoryStoreSize();
	}

}
