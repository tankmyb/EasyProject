package com.easy.persistance.creater.service.bean;

public class ServiceImplBean {

	private String packageName;
	private String className;
	private String daoName;
	private String serviceName;
	private String daoPath;
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
	public String getDaoName() {
		return daoName;
	}
	public void setDaoName(String daoName) {
		this.daoName = daoName;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getDaoPath() {
		return daoPath;
	}
	public void setDaoPath(String daoPath) {
		this.daoPath = daoPath;
	}
}
