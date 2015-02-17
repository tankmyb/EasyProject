package com.easy.kit.cache.memcache;

import java.util.Date;
import java.util.Map;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import com.easy.kit.utils.datetime.DateTimeUtil;

public class MemcacheHandler implements IMemcacheHandler{
	/**
	 * 默认过期时间
	 */
	private Date defaultExpiry;
	
	protected static IMemcacheHandler cacheHandler;
	
	/**
	 * Memcache客户端
	 */
    protected MemCachedClient mcc;
	
	private MemcacheHandler() {
		mcc = new MemCachedClient("COMMON");
		setDefaultExpiry();
	}
	
	/**
	 * @Title: 
	 *		setDefaultExpiry 
	 * @Description: 
	 *		Set default expiry date
	 *
	 */
	private void setDefaultExpiry(){
		int seconds = 3600;//默认延后1小时
		Date now = new Date();
		defaultExpiry = DateTimeUtil.addTime(now, 0,0,seconds);
	}
	
	/**
	 * @Title: 
	 *		getInstance 
	 * @Description: 
	 *		获得缓存处理对象唯一实例 
	 * @return 
	 *		ICacheHandle 缓存处理对象唯一实例
	 */
	public static IMemcacheHandler getInstance(){
		if(cacheHandler == null){
			initSockIOPool();
			cacheHandler = new MemcacheHandler();
		}
		return cacheHandler;
	}
	
	private static void initSockIOPool(){
		
		// 获取socke连接池的实例对象
        SockIOPool pool = SockIOPool.getInstance("COMMON");
        //没有被初始化
        try{
        	if(!pool.isInitialized()){
    	        // 设置服务器信息
    	        pool.setServers(new String[]{"127.0.0.1:11211"});
    	        pool.setFailover( true );    
    	        // 设置初始连接数、最小和最大连接数以及最大处理时间
    	        pool.setMinConn(5);
    	        pool.setMaxConn(40);
    	        pool.setMaxIdle(10000);
    	
    	        // 初始化连接池
    	        pool.initialize();
            }
        }catch(Exception e){
        	e.printStackTrace();
        }
        
        System.out.println("===================="+pool);
	}
	
	public boolean addObj(String key, Object obj, Date expiry) {
		return mcc.add(key, obj, expiry);
	}

	public boolean addObj(String key, Object obj) {
		return  mcc.add(key, obj, defaultExpiry);
	}

	public boolean deleteObj(String key) {
		return mcc.delete(key);
	}

	public Object getObj(String key) {
		return mcc.get(key);
	}

	public boolean updateObj(String key, Object obj, Date expiry) {
		return mcc.replace(key, obj, expiry);
	}

	public boolean updateObj(String key, Object obj) {
		return mcc.replace(key, obj, defaultExpiry);
	}

	@Override
	public Map<String, Map<String, String>> stats() {
		return mcc.stats();
	}

	@Override
	public boolean keyExists(String key) {
		return mcc.keyExists(key);
	}
	
}
