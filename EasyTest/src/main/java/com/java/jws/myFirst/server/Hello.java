package com.java.jws.myFirst.server;

import java.util.ArrayList;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(name="Hello")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface Hello {
	public ArrayList<Customer> getCustomerList();
}
