package com.easy.persistance.creater.row.bean;

import java.util.List;

public class RowBean {

	private String packageName;

	private String className;
	
	private List<RowFieldBean> fieldList;

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<RowFieldBean> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<RowFieldBean> fieldList) {
		this.fieldList = fieldList;
	}
} 
