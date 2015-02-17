package com.fastjson;

import com.alibaba.fastjson.JSON;

public class Test {

	public static void main(String[] args) {
		//String source = "{\"id1\":0,\"id\":0,\"name\":\"admin\",\"users\":[{\"id\":2,\"name\":\"guest\"},{\"id\":3,\"name\":\"root\"}]}";
		String source = "{\"id\":34,\"group\":{\"id1\":0,\"id\":0,\"name\":\"admin\",\"users\":[{\"id\":2,\"name\":\"guest\"},{\"id\":3,\"name\":\"root\"}]}}";
		User group2 = JSON.parseObject(source, User.class);
		System.out.println(group2);
	}
}
