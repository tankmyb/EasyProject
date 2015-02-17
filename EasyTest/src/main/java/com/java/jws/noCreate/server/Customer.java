package com.java.jws.noCreate.server;

import java.util.Date;  

import javax.activation.DataHandler;  
import javax.xml.bind.annotation.XmlAccessType;  
import javax.xml.bind.annotation.XmlAccessorType;  
import javax.xml.bind.annotation.XmlMimeType;  
import javax.xml.bind.annotation.XmlRootElement;  
  
/**
 * MTOM 方式中要传输的附件必须使用javax.activation.DataHandler 类，
 * 然后这个类型还要使用@javax.xml.binding.annotation.XmlMimeType 进行注解，
 * 标注这是一个附件类型的数据，这里我们标注imageData 是一个二进制文件，当然你也可以使用具体的MIME类型，
 * 譬如：image/jpg、image/gif 等，但你要考虑客户端是否有对应的类型（因为JAVA语言之外的客户端的特性未必是你完全了解的），
 * 而且javax.activation.*中的MIME 的相关API 可以完成MIME 类型的自动识别。
 * 
 * 注意的是必须在类上使用@XmlAccessorType(FIELD)注解，标注JAXB 在进行JAVA 对象与XML 之间进行转换时只关注字段，
 * 而不关注属性（getXXX()方法），否则发布Web 服务时会报出现了两个imageData 属性的错误，
 * 不知道这是不是CXF 或者是底层JAXB 实现的BUG，因为它并没有报其他的属性因为getXXX()方法的存在而视为重复属性
 * 
 * @author why
 *
 */
@XmlRootElement(name = "Customer")
@XmlAccessorType(XmlAccessType.FIELD)
public class Customer {
	private long id;
	private String name;
	private Date birthday;
	@XmlMimeType("application/octet-stream")
	private DataHandler imageData;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public DataHandler getImageData() {
		return imageData;
	}
	public void setImageData(DataHandler imageData) {
		this.imageData = imageData;
	}
}
