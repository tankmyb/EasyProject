package com.easy.kit.cache;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Repeat;

import com.easy.kit.BaseTest;

public class CacheTest extends BaseTest {

	@Resource
	private ICacheService service;
	
	@Test
	public void test(){
		service.add(1);
		service.add(1);
	}
}
