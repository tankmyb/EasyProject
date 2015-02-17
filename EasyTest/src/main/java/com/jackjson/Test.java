package com.jackjson;

import java.util.Date;

import org.junit.Before;

public class Test {

	private String source;
	@Before
	public void before(){
		source = "{\"id1\":3454,\"id\":34,\"group\":{\"id1\":0,\"id\":0,\"name\":\"admin\",\"users\":[{\"id\":2,\"name\":\"guest\"},{\"id\":3,\"name\":\"root\"}]}}";
	}
	@org.junit.Test
	public void testBuildNormalBinder(){
		User user = JsonUtil.buildNormalBinder().fromJson(source, User.class);
		System.out.println(user.getId());
	}
	@org.junit.Test
	public void testBuildNormalBinderToString(){
		JsonUtil json =JsonUtil.buildNonNullBinder();
		User user = new User();
		user.setId(new Long(3333));
		user.setCreateDate(new Date());
		String str = (json.toJson(user));
		System.out.println(str);
		UserTmp ut = json.fromJson(str, UserTmp.class);
		System.out.println(ut.getGroup());
		
	}
	@org.junit.Test
	public void test2(){
		JsonUtil json =JsonUtil.buildNonNullBinder();
		json.setDateFormat("yyyy-MM-dd HH:mm:ss");
		User user = new User();
		user.setId(new Long(3333));
		user.setCreateDate(new Date());
		String str = (json.toJson(user));
		System.out.println(str);
		User u = json.fromJson(str, User.class);
		System.out.println(u.getCreateDate());
		System.out.println(u.getGroup());
		
	}
	@org.junit.Test
	public void test1() throws Exception{
		User user = new User();
		user.setId(new Long(3333));
		String str = ResolveUnit.getJsonStr(user);
		System.out.println(str);
		UserTmp ut = ResolveUnit.resolve(str, UserTmp.class);
		
		System.out.println(ut.getId());
		
	}
}
