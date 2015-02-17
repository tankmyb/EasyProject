package com.guava;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.MapMaker;

public class MapMakerTest {

	static class Key{
		int id;
	}
	public static void testWeakKeys() throws Exception {  
	    ConcurrentMap<Key, Integer> map = new MapMaker()  
	        .weakKeys() // 指定Map保存的Key为WeakReference机制  
	        .makeMap();   
	  
	    Key key = new Key();  
	    map.put(key, new Integer(2)); // 加入元素  
	    key = null; // key变成了WeakReference  
	  
	    System.gc();// 触发垃圾回收  
	    TimeUnit.SECONDS.sleep(10L);  
	  
	    System.out.println(map.isEmpty()); // map空了，因为WeakReference被回收  
	}  
	public static void main(String[] args) throws Exception {
		LoadingCache<String,String> cahceBuilder=CacheBuilder
		        .newBuilder()
		        .expireAfterWrite(10, TimeUnit.SECONDS)
		        .build(new CacheLoader<String, String>(){
		            @Override
		            public String load(String key) throws Exception {        
		                return "1";
		            }
		            
		        });        
		        
		        System.out.println("jerry value:"+cahceBuilder.get("jerry"));
		        System.out.println("peida value:"+cahceBuilder.get("peida"));
		        //System.out.println("peida value:"+cahceBuilder.apply("peida"));
		        //System.out.println("lisa value:"+cahceBuilder.apply("lisa"));
		        cahceBuilder.put("peida", "ssdded");
		        System.out.println("harry value:"+cahceBuilder.get("harry"));
		    }
}
