package com.easy.persistance.creater.dao.bean;

public class DAOBean {

	private String packageName;
	private String ormPackageName;
	
	private String className;
	private String rowName;
	private String primaryKeyName;
	public String getOrmPackageName() {
		return ormPackageName;
	}
	public void setOrmPackageName(String ormPackageName) {
		this.ormPackageName = ormPackageName;
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
	public String getPrimaryKeyName() {
		return primaryKeyName;
	}
	public void setPrimaryKeyName(String primaryKeyName) {
		this.primaryKeyName = primaryKeyName;
	}
}
