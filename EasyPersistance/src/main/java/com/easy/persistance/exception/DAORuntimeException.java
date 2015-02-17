package com.easy.persistance.exception;

public class DAORuntimeException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	public DAORuntimeException(String message) {
		super(message);
	}

	public DAORuntimeException(String message,Throwable t) {
		super(message,t);
	}
	public DAORuntimeException(Throwable t) {
		super(t);
	}
}
