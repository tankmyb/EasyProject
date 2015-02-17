package com.java.jws.noCreate.server;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
  
/**
 * 使用类级别注解@WebService 就标注了这个接口的方法将公开为Web 服务，使用了这个注解的接口的所有方法都将公开为Web 服务的操作，
 * 如果你想屏蔽某个方法，可以使用方法注解@Method 的exclude=true。
 * 我们也通常把公开为Web服务的接口叫做SEI（Service EndPoint Interface）服务端点接口。
 * 
 * @author why
 *
 */
@WebService(name="Hello")
@SOAPBinding(style = SOAPBinding.Style.RPC)
//@MTOM
public interface Hello {
	public void printContext();
	public Customer selectCustomerByName(@WebParam(name = "c",header=true)Customer customer);
	public Customer selectMaxAgeCustomer(Customer c1, Customer c2);
}
