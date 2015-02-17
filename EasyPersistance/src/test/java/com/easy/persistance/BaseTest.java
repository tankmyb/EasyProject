package com.easy.persistance;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:beans-test.xml" })
public class BaseTest extends Assert {

	protected Logger logger = LoggerFactory.getLogger(BaseTest.class);

	@Test
	public void test(){
		
	}
}
