package com.easy.persistance.creater.dao.bean;

public class BaseDAOBean {

	private String packageName;
	private String className;
	private String rowName;
	private String selectName;
	private String tableName;
	private String primaryKeyName;
	public String getSelectName() {
		return selectName;
	}
	public void setSelectName(String selectName) {
		this.selectName = selectName;
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
	public String getRowName() {
		return rowName;
	}
	public void setRowName(String rowName) {
		this.rowName = rowName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getPrimaryKeyName() {
		return primaryKeyName;
	}
	public void setPrimaryKeyName(String primaryKeyName) {
		this.primaryKeyName = primaryKeyName;
	}
}
