
package com.java.jws.sayHello.sys;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "ServerService", targetNamespace = "http://sayHello.jws.java.com/", wsdlLocation = "http://localhost:8080/com.java.jws.sayHello.Server?wsdl")
public class ServerService
    extends Service
{

    private final static URL SERVERSERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(com.java.jws.sayHello.sys.ServerService.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = com.java.jws.sayHello.sys.ServerService.class.getResource(".");
            url = new URL(baseUrl, "http://localhost:8080/com.java.jws.sayHello.Server?wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'http://localhost:8080/com.java.jws.sayHello.Server?wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        SERVERSERVICE_WSDL_LOCATION = url;
    }

    public ServerService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ServerService() {
        super(SERVERSERVICE_WSDL_LOCATION, new QName("http://sayHello.jws.java.com/", "ServerService"));
    }

    /**
     * 
     * @return
     *     returns Server
     */
    @WebEndpoint(name = "ServerPort")
    public Server getServerPort() {
        return super.getPort(new QName("http://sayHello.jws.java.com/", "ServerPort"), Server.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns Server
     */
    @WebEndpoint(name = "ServerPort")
    public Server getServerPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://sayHello.jws.java.com/", "ServerPort"), Server.class, features);
    }

}
