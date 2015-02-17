package com.java.jws.noCreate.client;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.xml.namespace.QName;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeader;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

  
/**
 * SAAJ中使用MTOM方式上传附件时，需设置Content-Type为application/xop+xml; charset=utf-8; type="text/xml"，
 * 否则服务器端接收不到附件。
 * 
 * @author why
 *
 */
public class SoapClient {
	public static void main(String[] args) throws Exception{
		
	printContext();
		
//		selectCustomerByName();
		
	//	selectMaxAgeCustomer();
	}
	
	/**
	 * 调用一个无参函数
	 * @throws Exception
	 */
	public static void printContext() throws Exception{
		// 获取SOAP连接工厂
		SOAPConnectionFactory factory = SOAPConnectionFactory.newInstance();
		// 从SOAP连接工厂创建SOAP连接对象
		SOAPConnection connection = factory.createConnection();

		// 获取消息工厂
//		MessageFactory mFactory = MessageFactory.newInstance();
		// 也可用如下方式获取消息工厂
		QName serviceName = new QName("http://service.why.com/", "HelloService");
		QName portName = new QName("http://service.why.com/", "HelloServicePort");
		String endpointAddress = "http://127.0.0.1:8080/helloService";
		String operationNameString = "printContext";
		Service service = Service.create(serviceName);
		service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING,endpointAddress);
		Dispatch<SOAPMessage> dispatch = service.createDispatch(portName, SOAPMessage.class,
				Service.Mode.MESSAGE);
		BindingProvider bp = (BindingProvider) dispatch;
		Map<String, Object> rc = bp.getRequestContext();
		rc.put(BindingProvider.SOAPACTION_USE_PROPERTY, Boolean.TRUE);
		rc.put(BindingProvider.SOAPACTION_URI_PROPERTY, operationNameString);
		SOAPBinding soapBinding = (SOAPBinding)bp.getBinding();
		soapBinding.setMTOMEnabled(true);//启用MTOM传输附件方式
		MessageFactory mFactory = soapBinding.getMessageFactory();
		
		// 从消息工厂创建SOAP消息对象
		SOAPMessage message = mFactory.createMessage();
		// 创建SOAPPart对象
		SOAPPart part = message.getSOAPPart();
		// 创建SOAP信封对象
		SOAPEnvelope envelope = part.getEnvelope();
		// 创建SOAPHeader对象
		SOAPHeader header = message.getSOAPHeader();
		// 创建SOAPBody对象
		SOAPBody body = envelope.getBody();
		
		// 创建XML的根元素
		SOAPBodyElement bodyElementRoot = body.addBodyElement(new QName("http://server.why.com/", "printContext", "ns1"));
		
		// 访问Web服务地址
		SOAPMessage reMessage = connection.call(message, new URL("http://127.0.0.1:8080/helloService"));
		// 控制台输出返回的SOAP消息
		OutputStream os = System.out;
		reMessage.writeTo(os);
		
		connection.close();
	}
	
	/**
	 * 调用一个在soap:HEADER中传递参数的函数
	 * @throws Exception
	 */
	public static void selectCustomerByName() throws Exception{
		// 获取SOAP连接工厂
		SOAPConnectionFactory factory = SOAPConnectionFactory.newInstance();
		// 从SOAP连接工厂创建SOAP连接对象
		SOAPConnection connection = factory.createConnection();
		// 获取消息工厂
		MessageFactory mFactory = MessageFactory.newInstance();
		// 从消息工厂创建SOAP消息对象
		SOAPMessage message = mFactory.createMessage();
		// 创建SOAPPart对象
		SOAPPart part = message.getSOAPPart();
		// 创建SOAP信封对象
		SOAPEnvelope envelope = part.getEnvelope();
		// 创建SOAPHeader对象
		SOAPHeader header = message.getSOAPHeader();
		// 创建SOAPBody对象
		SOAPBody body = envelope.getBody();
		
		// 创建XML的根元素
		SOAPHeaderElement headerElementRoot = header.addHeaderElement(new QName("http://server.why.com/", "c", "ns1"));
		SOAPBodyElement bodyElementRoot = body.addBodyElement(new QName("http://server.why.com/", "selectCustomerByName", "ns1"));
		headerElementRoot.addChildElement(new QName("name")).addTextNode("why");
		
		// 访问Web服务地址
		SOAPMessage reMessage = connection.call(message, new URL("http://127.0.0.1:8080/helloService"));
		// 控制台输出返回的SOAP消息
		OutputStream os = System.out;
		reMessage.writeTo(os);
		
		// 输出SOAP消息中的附件
		Iterator<AttachmentPart> it = reMessage.getAttachments();
		while (it.hasNext()) {
			InputStream ins = it.next().getDataHandler().getInputStream();
			byte[] b = new byte[ins.available()];
			OutputStream ous = new FileOutputStream("c:\\aaa.jpg");
			while (ins.read(b) != -1) {
				ous.write(b);
			}
			ous.close();
		}
		connection.close();
	}
	
	/**
	 * 调用一个在soap:Body中传递参数的函数
	 * @throws Exception
	 */
	public static void selectMaxAgeCustomer() throws Exception{
		// 获取SOAP连接工厂
		SOAPConnectionFactory factory = SOAPConnectionFactory.newInstance();
		// 从SOAP连接工厂创建SOAP连接对象
		SOAPConnection connection = factory.createConnection();
		// 获取消息工厂
		MessageFactory mFactory = MessageFactory.newInstance();
		// 从消息工厂创建SOAP消息对象
		SOAPMessage message = mFactory.createMessage();
		// 创建SOAPPart对象
		SOAPPart part = message.getSOAPPart();
		// 创建SOAP信封对象
		SOAPEnvelope envelope = part.getEnvelope();
		// 创建SOAPHeader对象
		SOAPHeader header = message.getSOAPHeader();
		// 创建SOAPBody对象
		SOAPBody body = envelope.getBody();
		
		// 设置Content-Type
		MimeHeaders hd = message.getMimeHeaders(); 
		hd.setHeader("Content-Type", "application/xop+xml; charset=utf-8; type=\"text/xml\"");
		Iterator  ite = hd.getAllHeaders();
		while(ite.hasNext()){
			MimeHeader mimeHeader = (MimeHeader)ite.next();
			System.out.println(mimeHeader.getName()+"="+mimeHeader.getValue());
		}
		
		// 创建XML的根元素
		SOAPBodyElement bodyElementRoot = body.addBodyElement(new QName("http://server.why.com/", "selectMaxAgeCustomer", "ns1"));
		
		// 创建Customer实例1
		SOAPElement elementC1 = bodyElementRoot.addChildElement(new QName("arg0"));
		elementC1.addChildElement(new QName("id")).addTextNode("1");
		elementC1.addChildElement(new QName("name")).addTextNode("A");
		elementC1.addChildElement(new QName("birthday")).addTextNode("1989-01-28T00:00:00.000+08:00");
		// 创建附件对象
		AttachmentPart attachment = message.createAttachmentPart(new DataHandler(new FileDataSource("c:\\c1.jpg")));
		// 设置Content-ID
		attachment.setContentId("<" + UUID.randomUUID().toString() + ">");
		attachment.setMimeHeader("Content-Transfer-Encoding", "binary");
		message.addAttachmentPart(attachment);
		SOAPElement elementData = elementC1.addChildElement(new QName("imageData"));
		
//		elementData.addAttribute(envelope.createName("href"),
//                "cid:" + attachment.getContentId());
		// 添加XOP支持
		elementData.addChildElement(
				new QName("http://www.w3.org/2004/08/xop/include", "Include","xop"))
				.addAttribute(new QName("href"),"cid:" + attachment.getContentId().replaceAll("<", "").replaceAll(">", ""));
		
		// 创建Customer实例2
		SOAPElement elementC2 = bodyElementRoot.addChildElement(new QName("arg1"));
		elementC2.addChildElement(new QName("id")).addTextNode("2");
		elementC2.addChildElement(new QName("name")).addTextNode("B");
		elementC2.addChildElement(new QName("birthday")).addTextNode("1990-01-28T00:00:00.000+08:00");
		AttachmentPart attachment2 = message.createAttachmentPart(new DataHandler(new FileDataSource("c:\\c2.jpg")));
		attachment2.setContentId("<" + UUID.randomUUID().toString() + ">");
		message.addAttachmentPart(attachment2);
		SOAPElement elementData2 = elementC2.addChildElement(new QName("imageData"));
		
//		elementData2.addAttribute(envelope.createName("href"),
//                "cid:" + attachment2.getContentId());
		elementData2.addChildElement(
				new QName("http://www.w3.org/2004/08/xop/include", "Include","xop"))
				.addAttribute(new QName("href"),"cid:" + attachment2.getContentId().replaceAll("<", "").replaceAll(">", ""));
		
		// 控制台输出发送的SOAP消息
		OutputStream os = new ByteArrayOutputStream();
		message.writeTo(os);
		String soapStr = os.toString();
		System.out.println("\n@@@@@@@@@@@@@@@@@@发送的SOAP消息\n"+soapStr+"\n@@@@@@@@@@@@@@@@@@");
		
		// 访问Web服务地址
		SOAPMessage reMessage = connection.call(message, new URL("http://127.0.0.1:8080/helloService"));
		// 控制台输出返回的SOAP消息
		OutputStream baos = new ByteArrayOutputStream();
		reMessage.writeTo(baos);
		String soapStr2 = baos.toString();
		System.out.println("\n#############\n"+soapStr2+"\n################");
		
//		// 输出SOAP消息中的第一个子元素的元素名称
		System.out.println("\n<<<<<<<<<<<<<<<<<<<" + reMessage.getSOAPBody().getFirstChild().getLocalName());
		// 输出SOAP消息中的附件
		Iterator<AttachmentPart> it = reMessage.getAttachments();
		while (it.hasNext()) {
			InputStream ins = it.next().getDataHandler().getInputStream();
			byte[] b = new byte[1024];
			OutputStream ous = new FileOutputStream("c:\\bbb.jpg");
			while (ins.read(b) != -1) {
				ous.write(b);
			}
			ous.close();
		}
		
		connection.close();
	}
}