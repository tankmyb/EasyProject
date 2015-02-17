package com.netty.reconn.client;

public class ConnectionFailException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6730269849132338293L;

	public ConnectionFailException(String failMsg){
		super(failMsg);
	}
}
