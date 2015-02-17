package com.easy.persistance.bean;

public class SaveBean {

	private boolean success = true;
	
	private boolean isNotUpdateRecord;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public boolean isNotUpdateRecord() {
		return isNotUpdateRecord;
	}
	public void setNotUpdateRecord(boolean isNotUpdateRecord) {
		this.isNotUpdateRecord = isNotUpdateRecord;
	}
}
