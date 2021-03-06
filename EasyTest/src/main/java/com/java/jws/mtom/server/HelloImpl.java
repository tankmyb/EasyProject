package com.java.jws.mtom.server;

import java.io.File;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStream;  
import java.text.ParseException;  
import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.util.Set;  
import javax.activation.DataHandler;  
import javax.activation.FileDataSource;  
import javax.annotation.Resource;  
import javax.jws.WebService;  
import javax.xml.ws.WebServiceContext;  
import javax.xml.ws.handler.MessageContext;  
  
/** 
 *  
 * @author why 
 * 
 */  
@WebService(serviceName="HelloService",portName="HelloServicePort",targetNamespace="http://service.why.com/",endpointInterface="com.java.jws.mtom.server.Hello")  
public class HelloImpl implements Hello {  
      
    @Resource  
    private WebServiceContext context;  
      
    @Override  
    public void printContext(){  
        MessageContext ctx = context.getMessageContext();  
        Set<String> set = ctx.keySet();  
        for (String key : set) {  
            System.out.println("{" + key + "," + ctx.get(key) +"}");  
            try {  
                System.out.println("key.scope=" + ctx.getScope(key));  
            } catch (Exception e) {  
                System.out.println(key + " is not exits");  
            }  
        }  
    }  
      
    @Override  
    public Customer selectCustomerByName(Customer customer) {  
        if("why".equals(customer.getName())){  
            customer.setId(1);  
            try {  
                customer.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse("1985-10-07"));  
            } catch (ParseException e) {  
                e.printStackTrace();  
            }  
            customer.setImageData(new DataHandler(new FileDataSource(new File("c:"+ File.separator + "why.jpg"))));  
        }else{  
            customer.setId(2);  
            customer.setBirthday(new Date());  
            customer.setImageData(new DataHandler(new FileDataSource(new File("c:"+ File.separator + "origin.jpg"))));  
        }  
        return customer;  
    }  
      
    @Override  
    public Customer selectMaxAgeCustomer(Customer c1, Customer c2) {  
        try {  
            // 输出接收到的附件  
            System.out.println("c1.getImageData().getContentType()=" + c1.getImageData().getContentType());  
            InputStream is = c2.getImageData().getInputStream();  
            OutputStream os = new FileOutputStream("c:\\temp1.jpg");  
            byte[] bytes = new byte[1024];  
            int c;  
            while ((c = is.read(bytes)) != -1) {  
                os.write(bytes, 0, c);  
            }  
            os.close();  
              
            System.out.println("c2.getImageData().getContentType()=" + c2.getImageData().getContentType());  
            is = c2.getImageData().getInputStream();  
            os = new FileOutputStream("c:\\temp2.jpg");  
            bytes = new byte[1024];  
            while ((c = is.read(bytes)) != -1) {  
                os.write(bytes, 0, c);  
            }  
            os.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
          
        if (c1.getBirthday().getTime() > c2.getBirthday().getTime()){  
            return c2;  
        }  
        else{  
            return c1;  
        }  
    }  
}
