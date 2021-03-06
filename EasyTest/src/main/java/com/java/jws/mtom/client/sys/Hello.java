
package com.java.jws.mtom.client.sys;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "Hello", targetNamespace = "http://server.mtom.jws.java.com/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface Hello {


    /**
     * 
     */
    @WebMethod
    public void printContext();

    /**
     * 
     * @param customer
     * @return
     *     returns com.java.jws.mtom.client.sys.Customer
     */
    @WebMethod
    @WebResult(partName = "return")
    public Customer selectCustomerByName(
        @WebParam(name = "customer", partName = "customer")
        Customer customer);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns com.java.jws.mtom.client.sys.Customer
     */
    @WebMethod
    @WebResult(partName = "return")
    public Customer selectMaxAgeCustomer(
        @WebParam(name = "arg0", partName = "arg0")
        Customer arg0,
        @WebParam(name = "arg1", partName = "arg1")
        Customer arg1);

}
