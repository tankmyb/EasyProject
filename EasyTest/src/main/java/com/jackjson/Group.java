package com.jackjson;

import java.util.ArrayList;
import java.util.List;

public class Group {
	private Long id;
	private String name;
	private List users =new ArrayList();
	
  private Child child;
  private List<Child> childList;
	public List<Child> getChildList() {
		return childList;
	}

	public void setChildList(List<Child> childList) {
		this.childList = childList;
	}

	public Child getChild() {
		return child;
	}

	public void setChild(Child child) {
		this.child = child;
	}

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
