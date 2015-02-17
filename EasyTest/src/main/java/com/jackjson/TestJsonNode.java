package com.jackjson;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonNode;

public class TestJsonNode {

	public static void main(String[] args) {
		User user = new User();
		user.setId(10L);
		user.setName("111111111");
		Group g1 = new Group();
		g1.setName("111");
		
		Child c1 = new Child();
		c1.setChildren("children");
		g1.setChild(c1);
		
		List<Child> childList = new ArrayList<Child>();
		childList.add(c1);
		g1.setChildList(childList);
		user.setGroup(g1);
		String str = ResolveUnit.getJsonStr(user);
		System.err.println(str);
		List<JsonNode> list = ResolveUnit.getNode(str, "group:childList");
		System.out.println(list.get(0));
	}
}
