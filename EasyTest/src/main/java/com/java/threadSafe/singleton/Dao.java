package com.java.threadSafe.singleton;

public class Dao {

	private static Dao dao = new Dao();
	private Dao(){
		
	}
	public static Dao getInstance(){
		return dao;
	}
}
