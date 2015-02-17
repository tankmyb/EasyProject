package com.protobuf;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;

import com.google.protobuf.InvalidProtocolBufferException;
import com.protobuf.testProtobuf.HeaderBuf;
import com.protobuf.testProtobuf.PersonProtobuf;
import com.protobuf.testProtobuf.PersonProtobuf.Person;
import com.protobuf.testProtobuf.PersonProtobuf.Person.PhoneNumber;

/**
 * 测试PersonProbuf.java
 * */
public class TestPersonProbuf {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// 序列化过程
		// PersonProtobuf是生成的数据访问类的名字，即proto文件中的java_outer_classname
		// Person是里面某个消息序列的名字，即proto文件中的message Person
		PersonProtobuf.Person.Builder builder = PersonProtobuf.Person.newBuilder();
		//设置消息序列中的字段值
		builder.setEmail("zhuxun777@gmail.com");
		builder.setId(320324);
		builder.setName("Jack Zhu");
		HeaderBuf.Header.Builder header = HeaderBuf.Header.newBuilder();
		header.setId(12);
		header.setName("aaaaaaa");
		builder.setHeader(header);
		//phone字段在person.proto文件中被定义为repeated型，可以放多个值
		//（在本例中，phone字段的数据类型为消息类型PhoneNumber）
		builder.addPhone(PersonProtobuf.Person.PhoneNumber.newBuilder().setNumber("18762678793").setType(Person.PhoneType.HOME));
		builder.addPhone(PersonProtobuf.Person.PhoneNumber.newBuilder().setNumber("18651581021").setType(Person.PhoneType.HOME));
		Person person = builder.build();
		byte[] buf = person.toByteArray();
		
		//把序列化后的数据写入本地磁盘
		ByteArrayInputStream stream = new ByteArrayInputStream(buf);
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("e:/protobuf.txt"));//设置输出路径
		BufferedInputStream bis = new BufferedInputStream(stream);
		int b = -1;
		while ((b = bis.read()) != -1) {
			bos.write(b);
		}
		bis.close();
		bos.close();
		
		
		//读取序列化后的数据
		try {
			Person person01 = PersonProtobuf.Person.parseFrom(buf);
			List<PhoneNumber> phones = person01.getPhoneList();
			String strPhone = "";
			for(PhoneNumber phone : phones){
				strPhone += phone.getNumber() + "   ";
			}
			//String strResult = person01.getName() + "," + person01.getId() + "," + person01.getEmail() + "," + strPhone;
			//System.out.println(strResult);
			System.out.println(person01.getHeader().getId());
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
		
		//读取序列化后写入磁盘的数据
		try {
			BufferedInputStream bis2 = new BufferedInputStream(new FileInputStream("e:/protobuf.txt"));
			byte b2 = -1;
			List<Byte> list = new LinkedList<Byte>();
			while ((b2 = (byte) bis2.read()) != -1) {
				list.add(b2);
			}
			bis2.close();
			int length = list.size();
			byte[] byt = new byte[length];
			for(int i = 0; i < length; i++){
				byt[i] = list.get(i);
			}
			Person person01 = PersonProtobuf.Person.parseFrom(byt);
			List<PhoneNumber> phones = person01.getPhoneList();
			String strPhone = "";
			for(PhoneNumber phone : phones){
				strPhone += phone.getNumber() + "   ";
			}
			String strResult = person01.getName() + "," + person01.getId() + "," + person01.getEmail() + "," + strPhone;
			System.out.println(strResult);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}

}
