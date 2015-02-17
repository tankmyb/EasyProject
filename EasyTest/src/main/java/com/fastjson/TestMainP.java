package com.fastjson;

import com.alibaba.fastjson.JSON;

public class TestMainP {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestMainP.test1();
		TestMainP.test2("{\"id1\":0,\"id\":0,\"name\":\"admin\",\"users\":[{\"id\":2,\"name\":\"guest\"},{\"id\":3,\"name\":\"root\"}]}");

	}

	private static String test1() {
		Group group = new Group();
		group.setId(0L);
		group.setName("admin");
		User guestUser = new User();
		guestUser.setId(2L);
		guestUser.setName("guest");
		User rootUser = new User();
		rootUser.setId(3L);
		rootUser.setName("root");
		group.getUsers().add(guestUser);
		group.getUsers().add(rootUser);
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			String jsonString = JSON.toJSONString(group);
		}
		System.out.println(System.currentTimeMillis() - start);
		return null;
	}

	private static Group test2(String source) {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			Group group2 = JSON.parseObject(source, Group.class);
		}
		System.out.println(System.currentTimeMillis() - start);
		return null;
	}
}
