package com.jackjson;

import java.util.Date;

public class User {
	private Long id;
	private String name;
  private Group group;
  private Date createDate;
	public Date getCreateDate() {
	return createDate;
}

public void setCreateDate(Date createDate) {
	this.createDate = createDate;
}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
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
}