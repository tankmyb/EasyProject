package com.easy.kit;

import junit.framework.Assert;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/beans.xml" })
public class BaseTest extends Assert {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

}
