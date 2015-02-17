package com.easy.persistance.exception;

public class UpdateNoRecordException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	public UpdateNoRecordException(String message) {
		super(message);
	}
}
