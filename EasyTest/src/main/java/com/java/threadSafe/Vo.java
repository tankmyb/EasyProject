package com.java.threadSafe;

public class Vo{
	private String name;
  private Integer id;
  private Child child;
	public Child getChild() {
		return child;
	}

	public void setChild(Child child) {
		this.child = child;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	private String className;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
