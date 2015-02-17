package com.cache.map;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class CacheMap<K,V> {

	private int expireSecond;
	private TimeUnit timeUnit;
	private ConcurrentHashMap<K,V> map1 = new ConcurrentHashMap<K,V>();
	private ConcurrentHashMap<K,V> map2 = new ConcurrentHashMap<K,V>();
	private ConcurrentHashMap<K,V> map3 = new ConcurrentHashMap<K,V>();
	private boolean clear1 = true;
	private boolean clear2 = true;
	private boolean clear3 = true;
	
	public CacheMap(int expireSecond,TimeUnit timeUnit){
		if(timeUnit==TimeUnit.MICROSECONDS || timeUnit==TimeUnit.NANOSECONDS || timeUnit==TimeUnit.MILLISECONDS){
			throw new RuntimeException("只支付秒、分、时、天");
		}
		this.expireSecond = expireSecond;
		this.timeUnit = timeUnit;
	}
	
}
