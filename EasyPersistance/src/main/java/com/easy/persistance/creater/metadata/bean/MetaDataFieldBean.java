package com.easy.persistance.creater.metadata.bean;

import java.io.Serializable;

public class MetaDataFieldBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String javaFieldName;
	
	private String tableFieldName;

	

	public String getTableFieldName() {
		return tableFieldName;
	}

	public void setTableFieldName(String tableFieldName) {
		this.tableFieldName = tableFieldName;
	}

	public String getJavaFieldName() {
		return javaFieldName;
	}

	public void setJavaFieldName(String javaFieldName) {
		this.javaFieldName = javaFieldName;
	}
}
