package com.easy.persistance.creater.row.bean;

public class RowFieldBean {
	private String tableFieldName;
	private String javaFieldName;
	private String fieldType;
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
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

}
