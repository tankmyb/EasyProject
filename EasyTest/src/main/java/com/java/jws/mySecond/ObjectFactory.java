
package com.java.jws.mySecond;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.java.jws.mysecond package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ListUser_QNAME = new QName("http://mySecond.jws.java.com/", "listUser");
    private final static QName _Hello_QNAME = new QName("http://mySecond.jws.java.com/", "hello");
    private final static QName _ListUserResponse_QNAME = new QName("http://mySecond.jws.java.com/", "listUserResponse");
    private final static QName _HelloResponse_QNAME = new QName("http://mySecond.jws.java.com/", "helloResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.java.jws.mysecond
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ListUserResponse }
     * 
     */
    public ListUserResponse createListUserResponse() {
        return new ListUserResponse();
    }

    /**
     * Create an instance of {@link Hello }
     * 
     */
    public Hello createHello() {
        return new Hello();
    }

    /**
     * Create an instance of {@link HelloResponse }
     * 
     */
    public HelloResponse createHelloResponse() {
        return new HelloResponse();
    }

    /**
     * Create an instance of {@link ListUser }
     * 
     */
    public ListUser createListUser() {
        return new ListUser();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mySecond.jws.java.com/", name = "listUser")
    public JAXBElement<ListUser> createListUser(ListUser value) {
        return new JAXBElement<ListUser>(_ListUser_QNAME, ListUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Hello }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mySecond.jws.java.com/", name = "hello")
    public JAXBElement<Hello> createHello(Hello value) {
        return new JAXBElement<Hello>(_Hello_QNAME, Hello.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mySecond.jws.java.com/", name = "listUserResponse")
    public JAXBElement<ListUserResponse> createListUserResponse(ListUserResponse value) {
        return new JAXBElement<ListUserResponse>(_ListUserResponse_QNAME, ListUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HelloResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mySecond.jws.java.com/", name = "helloResponse")
    public JAXBElement<HelloResponse> createHelloResponse(HelloResponse value) {
        return new JAXBElement<HelloResponse>(_HelloResponse_QNAME, HelloResponse.class, null, value);
    }

}
