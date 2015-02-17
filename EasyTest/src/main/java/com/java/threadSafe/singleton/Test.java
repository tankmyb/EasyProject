package com.java.threadSafe.singleton;

import com.jackjson.JsonUtil;
import com.java.threadSafe.Child;
import com.java.threadSafe.Vo;

public class Test {

	public static void main(String[] args) {
		Vo vo = new Vo();
		vo.setId(1);
		vo.setName("name1");
		
		Child child = new Child();
		child.setClassess("c1");
		child.setSex("male");
		vo.setChild(child);
		
		JsonUtil json =JsonUtil.buildNonNullBinder();
		System.out.println(json.toJson(vo));

	}
}
