package com.java.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
	static final int BUFFER=2048; 
	static boolean flag=false;
	
	 public static File ZipSubdirectory(File myDir)throws Exception{
			//创建缓冲输入流BufferedInputStream 
			BufferedInputStream origin=null;
			//创建ZipOutputStream对象，将向它传递希望写入文件的输出流
			File zipFile=new File(myDir.getAbsolutePath()+".zip");
			FileOutputStream fos=new FileOutputStream(zipFile);
			ZipOutputStream out=new ZipOutputStream(new BufferedOutputStream(fos,BUFFER));
			//dirContents[]获取当前目录(myDir)所有文件对象（包括子目录名)
			File dirContents[]=myDir.listFiles();
			//创建临时文件tempFile,使用后删除
			File tempFile=null; 
			try{
			//处理当前目录所有文件对象，包括子目录
			for(int i=0;i<dirContents.length;i++){
				//使用递归方法将当前目录的子目录转成一个ZIP文件，并作为一个ENTRY加进"ORIGIN" 
				if(dirContents[i].isDirectory()){
					tempFile=ZipSubdirectory(dirContents[i]);
					flag=true;
				}
				//假如当前文件不是子目录
				else{
					tempFile=dirContents[i];
					//flag标记tempFile是否由子目录压缩成的ZIP文件
					flag=false;
				}
				System.out.println("Compress file: "+tempFile.getName());
				FileInputStream fis=new FileInputStream(tempFile);
				origin=new BufferedInputStream(fis,BUFFER);
				//为被读取的文件创建压缩条目
				ZipEntry entry=new ZipEntry(tempFile.getName());
				byte data[]=new byte[BUFFER];
				int count;
				//在向ZIP输出流写入数据之前，必须首先使用out.putNextEntry(entry); 方法安置压缩条目对象 
				out.putNextEntry(entry);
				//向ZIP 文件写入数据 
				while((count=origin.read(data,0,BUFFER))!=-1){
					out.write(data,0,count);
				}
				//关闭输入流 
				origin.close(); 
				//tempFile是临时生成的ZIP文件,删除它
				if(flag==true){
					System.out.println(tempFile.getAbsolutePath());
					flag=tempFile.delete();
					System.out.println("Delete file:"+tempFile.getName()+flag); 
				} 
				
			}
			out.close();
		}catch(Exception e){
			System.out.println(e);
			throw e;
		}
			//递归返回 
			return zipFile; 
		}
	 /**  
	   * 创建目录，包括子目录  
	   * @param dir  
	   * @throws Exception  
	   */
	  private static void mkDirs(String dir) throws Exception{   
	      if(dir == null || dir.equals("")) return;   
	      File f1 = new File(dir);   
	      if(!f1.exists())   
	          f1.mkdirs();   
	  }     
	  /**
	   * 解压缩文件zipFile保存在directory目录下
	   *
	   * @param directory
	   * @param zipFile
	   */
	  public static void unZip(String directory, String zipFile) {
	   try {
	    ZipInputStream zis = new ZipInputStream(
	      new FileInputStream(zipFile));
	    File f = new File(directory);
	    f.mkdirs();
	    fileUnZip(zis, f);
	    zis.close();
	   } catch (Exception e) {
	    e.printStackTrace();
	   }
	  }
	  /**
	   * 解压缩文件
	   *
	   * @param zis
	   * @param file
	   * @throws Exception
	   */
	  private static void fileUnZip(ZipInputStream zis, File file)
	    throws Exception {
	   ZipEntry zip = zis.getNextEntry();
	   if (zip == null)
	    return;
	   String name = zip.getName();
	   File f = new File(file.getAbsolutePath() + "/" + name);
	   if (zip.isDirectory()) {
	    f.mkdirs();
	    fileUnZip(zis, file);
	   } else {
	    f.createNewFile();
	    FileOutputStream fos = new FileOutputStream(f);
	    byte b[] = new byte[2048];
	    int aa = 0;
	    while ((aa = zis.read(b)) != -1) {
	     fos.write(b, 0, aa);
	    }
	    fos.close();
	    fileUnZip(zis, file);
	   }
	  }
	 public static void main(String[] args) {
		 Date start = new Date();
	  try {
	   //t.zip("D://traceSys.zip", "D://traceSys");
	   ZipUtil.unZip("d://n","D://traceSys.zip");
	   System.out.println(new Date().getTime() - start.getTime());
	  } catch (Exception e) {
	   e.printStackTrace(System.out);
	  }
	  
	 }
}
