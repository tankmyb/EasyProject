package com.fastjson;

import java.util.ArrayList;
import java.util.List;

public class Group {
	private Long id;
	private String name;
	private List users =new ArrayList();
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List getUsers() {
		return users;
	}

	public void setUsers(List users) {
		this.users = users;
	}
}
