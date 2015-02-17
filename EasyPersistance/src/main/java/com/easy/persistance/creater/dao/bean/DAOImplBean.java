package com.easy.persistance.creater.dao.bean;

public class DAOImplBean {

	private String packageName;
	private String ormPackageName;
	
	private String className;
	private String baseDAOName;
	private String daoName;
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
	
	public String getBaseDAOName() {
		return baseDAOName;
	}
	public void setBaseDAOName(String baseDAOName) {
		this.baseDAOName = baseDAOName;
	}
	public String getDaoName() {
		return daoName;
	}
	public void setDaoName(String daoName) {
		this.daoName = daoName;
	}
}
