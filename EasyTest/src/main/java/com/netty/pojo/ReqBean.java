package com.netty.pojo;

import java.io.Serializable;

public class ReqBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5778363122416596561L;

	private String id;
	
	private String reqMsg;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReqMsg() {
		return reqMsg;
	}

	public void setReqMsg(String reqMsg) {
		this.reqMsg = reqMsg;
	}
}
