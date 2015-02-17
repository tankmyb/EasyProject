package com.netty.kryo.pojo;

import java.io.Serializable;


public class Person implements Serializable {
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	private static final long serialVersionUID = -3005546218059286518L;

	private Integer id;
	private String name;
	private String email;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
