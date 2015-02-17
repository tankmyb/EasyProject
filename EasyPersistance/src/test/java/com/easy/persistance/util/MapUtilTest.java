package com.easy.persistance.util;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.easy.persistance.Sub;
import com.easy.persistance.orm.CourseRow;


public class MapUtilTest {

	@Test
	public void test1() throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException{
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("name", "name");
		map.put("age", 3);
		Sub sub = MapUtil.convertMap(Sub.class, map);
		System.out.println(sub.getAge());
	}
	@Test
	public void test2() throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException{
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("cou_name", "name");
		CourseRow sub = MapUtil.convertMap(CourseRow.class, map);
		System.out.println(sub.getCouName());
	}
}
