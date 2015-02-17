package com.mina.transaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ActivityMain {

	public static void main(String[] args) throws IOException {
		TransactionClient client = new TransactionClient("activity");
		List<String> list = new ArrayList<String>();
		list.add("order");
		list.add("concession");
		list.add("33");
		list.add("44");
		//client.send(list);
		BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
	    for(;;){
	    	String str = reader.readLine();
	    	if(str.equals("quit")){
	    		break;
	    	}
	    	client.send(list);
	     //buf.clear();
	    }
		
	}
}
