package com.netty.async;

import java.io.Serializable;

public class RespBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4120811119472778201L;

	private String id;
	
	private String respMsg;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
}
