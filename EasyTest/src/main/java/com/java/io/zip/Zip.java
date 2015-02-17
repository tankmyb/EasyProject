package com.java.io.zip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Zip {

	public  static void unzip(byte[] data) throws IOException {
		ZipOutputStream fos = null;
		ZipInputStream zis = null;
		System.out.println(data.length+"===========len");
		try {   
			zis = new ZipInputStream(new ByteArrayInputStream(data));
			//zis = new ZipInputStream(new FileInputStream("D:\\20.zip"));
			System.out.println(zis+"===");
			File zipFile=new File("D:\\20tmp1.zip");
		    fos=new ZipOutputStream(new FileOutputStream(zipFile));
		    
		    int num =-1;
		    ZipEntry entry = null;
		    while ((entry=zis.getNextEntry()) != null) {
		    	 System.out.println(entry);
		    	byte[] buf = new byte[2048];
		      num = -1;
		      fos.putNextEntry(entry);
		     while ((num = zis.read(buf)) != -1) {
		    	
		    	 fos.write(buf, 0, num);
		     }
		     fos.flush();
		    }
		} catch (IOException e) {  
			e.printStackTrace();
			throw e;   
		} finally {  
			fos.close();
			zis.close();
		}   
	}
	public  static byte[] zipByte(File inputFile,int count, String zipFilename) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ZipOutputStream out = new ZipOutputStream(bos); 
		byte[] b = null;
		try {   
			zip(inputFile, out, "",count); 
			
		} catch (IOException e) {  
			e.printStackTrace();
			throw e;   
		} finally {  
			out.closeEntry();//这里很重要，一定要先close，再获取字节数组，不然流会损坏。。。
			out.close();
			b = bos.toByteArray();
			bos.close();
		}   
		return b;
	} 
	public  static void zip(File inputFile,int count, String zipFilename) throws IOException {
		File zipFile=new File(zipFilename);
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile)); 
		try {   
			zip(inputFile, out, "",count); 
		} catch (IOException e) {  
			e.printStackTrace();
			throw e;   
		} finally {  
			
			out.close();  
			
		}   
	}   

	private static void zip(File inputFile, ZipOutputStream out, String base,int count)   
	throws IOException {   
		if (inputFile.isDirectory()) {   
			File[] inputFiles = inputFile.listFiles();   
			out.putNextEntry(new ZipEntry(base + "/"));   
			base = base.length() == 0 ? "" : base + "/";   
			for (int i = 0; i < count; i++) {   
				zip(inputFiles[i], out, base + inputFiles[i].getName(),count); 
				//inputFiles[i].delete();
			}   

		} else {   
			if (base.length() > 0) {   
				out.putNextEntry(new ZipEntry(base));   
			} else {   
				out.putNextEntry(new ZipEntry(inputFile.getName()));   
			}   

			FileInputStream in = new FileInputStream(inputFile);   
			try {   
				int c;   
				byte[] by = new byte[1024];   
				while ((c = in.read(by)) != -1) {   
					out.write(by, 0, c);   
				}   
			} catch (IOException e) {   
				throw e;   
			} finally {   
				in.close();   
			}   
		}   
	}   
	public static void main(String[] args) throws Exception {
		Date start = new Date();
		//ZipUtil.ZipSubdirectory(new File("D://traceSys"));
		Zip.zip(new File("E:\\project\\codeService\\web\\cache\\14"),100,"d://20.zip");

		//Zip.unzip(Zip.zipByte(new File("D://20"),5,"d://20.zip"));
		System.out.println(new Date().getTime() - start.getTime());
		
	}
}
