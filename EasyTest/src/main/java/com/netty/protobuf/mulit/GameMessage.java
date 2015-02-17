package com.netty.protobuf.mulit;

import com.google.protobuf.MessageLite;

public class GameMessage {

	private int id;
	private MessageLite message;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public MessageLite getMessage() {
		return message;
	}
	public void setMessage(MessageLite message) {
		this.message = message;
	}
}
