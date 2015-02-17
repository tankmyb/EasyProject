package com.easy.persistance.creater.select.bean;

import java.io.Serializable;
import java.util.List;

public class SelectBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String packageName;

	private String className;
	
	private String metadataName;
	
	private List<SelectFieldBean> fieldList;

	public List<SelectFieldBean> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<SelectFieldBean> fieldList) {
		this.fieldList = fieldList;
	}

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

	public String getMetadataName() {
		return metadataName;
	}

	public void setMetadataName(String metadataName) {
		this.metadataName = metadataName;
	}
}
