package com.memcached;

import java.util.Date;
import java.util.Map;

/**
 * @ClassName: 
 *		IMemcacheHandler 
 * @Description: 
 *		Memcache handler interface
 * @author:
 *		tangzhu
 * @date 
 *		2010-8-26 下午04:17:44 
 *
 */
public interface IMemcacheHandler {
	/**
	 * 向缓存中添加Obj，并设置过期时间
	 */
	public boolean addObj(String key, Object obj,Date expiry);

	/**
	 * 向缓存中添加对象
	 */
	public boolean addObj(String key, Object obj);
	
	/**
	 * 向缓存中删除对象
	 */
	public boolean deleteObj(String key);
	
	/**
	 * 查询对象
	 */
	public Object getObj(String key);

	/**
	 * 修改缓存中的对象
	 */
	public boolean updateObj(String key, Object obj);

	/**
	 * 修改缓存中的对象，并设置过期时间
	 */
	public boolean updateObj(String key, Object obj,Date expiry);
	
	/**
	 * 
	 * @Title: 
	 *		stats 
	 * @Description: 
	 *		获取当前Memcache服务端状态
	 * @return 
	 *		Map<String,Map<String,String>>
	 */
	public Map<String,Map<String,String>> stats();
	
	/**
	 * @Title: 
	 *		keyExists 
	 * @Description: 
	 *		Checks to see if key exists in cache. 
	 * @param 
	 *      @param key
	 * @return 
	 *		boolean
	 */
	public boolean keyExists(String key);
}
