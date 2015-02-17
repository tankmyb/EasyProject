package com.easy.kit.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

	public static Properties getProperties(String fileName){
		//return getProperties(FileUtil.getInputStream(fileName));
		File file = new File(fileName);
		if(file.exists()){
			InputStream is = null;
			try {
				is = new FileInputStream(file);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			return getProperties(is);
		}else {
			return getProperties(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName));
		}
		
	}
	
	public static Properties getProperties(InputStream is){
		Properties props=new Properties();
		try{
			props.load(is);
			is.close();
			return props;
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}
	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getContextClassLoader().getResourceAsStream("beans-aop.xml"));
	}
}
