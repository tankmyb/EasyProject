
package com.java.jws.mythird.client;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import com.java.jws.mythird.server.ObjectFactory;
import com.java.jws.mythird.server.User;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "ServiceDemo", targetNamespace = "http://server.mythird.jws.java.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ServiceDemo {


    /**
     * 
     * @return
     *     returns java.util.List<com.java.jws.mythird.server.User>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "listUser", targetNamespace = "http://server.mythird.jws.java.com/", className = "com.java.jws.mythird.server.ListUser")
    @ResponseWrapper(localName = "listUserResponse", targetNamespace = "http://server.mythird.jws.java.com/", className = "com.java.jws.mythird.server.ListUserResponse")
    public List<User> listUser();

}
