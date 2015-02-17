package com.easy.kit.cache;

import com.easy.kit.cache.ehcache.CacheableEx;

public class CacheService implements ICacheService{

	@Override
	//@CacheableEx(type=CacheUtil.CACHE,key="add")
	public String add(int a) {
		System.out.println(a+"====");
		return "aaa";
	}

}
