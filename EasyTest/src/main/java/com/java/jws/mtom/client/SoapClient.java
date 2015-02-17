package com.java.jws.mtom.client;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.namespace.QName;

import com.java.jws.mtom.client.sys.Customer;
import com.java.jws.mtom.client.sys.Hello;
import com.java.jws.mtom.client.sys.HelloService;

public class SoapClient {

	 public static void main(String[] args) throws ParseException, MalformedURLException {  
	        QName qName = new QName("http://service.why.com/","HelloService");  
	        HelloService helloService = new HelloService(new URL("http://127.0.0.1:8080/helloService?wsdl"),qName);  
	        Hello hello = (Hello) helloService.getPort(Hello.class);  
	          
	        hello.printContext();  
	          
	        System.out.println("---------------------------------------------------");  
	          
	        Customer customer = new Customer();  
	        customer.setName("why");  
	        DataSource ds = hello.selectCustomerByName(customer).getImageData().getDataSource();  
	        String attachmentMimeType = ds.getContentType();  
	        System.out.println(attachmentMimeType);  
	        try {  
	            InputStream is = ds.getInputStream();  
	            OutputStream os = new FileOutputStream("c:\\why_temp.jpg");  
	            byte[] bytes = new byte[1024];  
	            int c;  
	            while ((c = is.read(bytes)) != -1) {  
	                os.write(bytes, 0, c);  
	            }  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	          
	        System.out.println("########################################");  
	          
	        Customer c1 = new Customer();  
	        c1.setId(1);  
	        c1.setName("why");  
	        GregorianCalendar calendar = (GregorianCalendar)GregorianCalendar.getInstance();  
	        calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse("1985-10-07"));  
	        try {  
	            c1.setBirthday(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));  
	        } catch (DatatypeConfigurationException e) {  
	            e.printStackTrace();  
	        }  
	        c1.setImageData(new DataHandler(new FileDataSource("c:\\c1.jpg")));  
	          
	        Customer c2 = new Customer();  
	        c2.setId(2);  
	        c2.setName("abc");  
	        calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse("1986-10-07"));  
	        try {  
	            c2.setBirthday(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));  
	        } catch (DatatypeConfigurationException e) {  
	            e.printStackTrace();  
	        }  
	        c2.setImageData(new DataHandler(new FileDataSource("c:\\c2.jpg")));  
	          
	        Customer c = hello.selectMaxAgeCustomer(c1,c2);  
	        System.out.println(c.getBirthday());  
	          
	    }  
}
