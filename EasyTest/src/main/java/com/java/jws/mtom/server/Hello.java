package com.java.jws.mtom.server;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.soap.MTOM;

@WebService(name="Hello")  
@SOAPBinding(style = SOAPBinding.Style.RPC)  
@MTOM  
public interface Hello {  
    public void printContext();  
    public Customer selectCustomerByName(@WebParam(name = "customer")Customer customer);  
    public Customer selectMaxAgeCustomer(Customer c1, Customer c2);  
}
