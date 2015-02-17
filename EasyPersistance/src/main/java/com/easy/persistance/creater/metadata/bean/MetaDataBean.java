package com.easy.persistance.creater.metadata.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MetaDataBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String packageName;

	private String className;

	private String tableName;

	private String javaTableName;

	private String tableAlias;

	private String primaryKeys;

	private String isAutoIncrement;

	private String pk;
	
	private List<MetaDataFieldBean> fieldList;

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

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getJavaTableName() {
		return javaTableName;
	}

	public void setJavaTableName(String javaTableName) {
		this.javaTableName = javaTableName;
	}

	public String getTableAlias() {
		return tableAlias;
	}

	public void setTableAlias(String tableAlias) {
		this.tableAlias = tableAlias;
	}

	public String getPrimaryKeys() {
		return primaryKeys;
	}

	public void setPrimaryKeys(String primaryKeys) {
		this.primaryKeys = primaryKeys;
	}


	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getIsAutoIncrement() {
		return isAutoIncrement;
	}

	public void setIsAutoIncrement(String isAutoIncrement) {
		this.isAutoIncrement = isAutoIncrement;
	}

	public List<MetaDataFieldBean> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<MetaDataFieldBean> fieldList) {
		this.fieldList = fieldList;
	}
}
