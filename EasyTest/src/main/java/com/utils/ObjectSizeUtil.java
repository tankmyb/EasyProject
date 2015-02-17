package com.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectSizeUtil {
	 public static int size(final Object o)  {
		  if (o == null) {
		   return 0;
		  }
		  ByteArrayOutputStream buf = new ByteArrayOutputStream(4096);
		  ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(buf);
			 out.writeObject(o);
			 out.flush();
			  buf.close();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		 
		  

		  return buf.size();
		 }
}
