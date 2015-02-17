package com.easy.kit.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import com.easy.kit.utils.log.ExceptionLogger;
public class ZipUtil {

	 /**  
   *   
   * @param inputFileName 输入一个文件夹  
   * @param zipFileName   输出一个压缩文件夹，打包后文件名字  
   * @throws Exception  
   */   
  public static void zip(String inputFileName, String zipFileName) throws Exception {     
      zip(zipFileName, new File(inputFileName));   
  }   

  private static void zip(String zipFileName, File inputFile) throws Exception {   
      ZipOutputStream out = new ZipOutputStream(new FileOutputStream(   
              zipFileName));  
      out.setEncoding("GBK");   

      zip(out, inputFile, "");   
      System.out.println("zip done");   
      out.close();   
  }   

  private static void zip(ZipOutputStream out, File f, String base) throws Exception {   
      if (f.isDirectory()) {  //判断是否为目录   
          File[] fl = f.listFiles();   
          out.putNextEntry(new ZipEntry(base + "/"));   
          base = base.length() == 0 ? "" : base + "/";   
          for (int i = 0; i < fl.length; i++) {   
              zip(out, fl[i], base + fl[i].getName());   
          }   
      } else {                //压缩目录中的所有文件   
          out.putNextEntry(new ZipEntry(base));   
          FileInputStream in = new FileInputStream(f);   
          int b;   
          System.out.println(base);   
          while ((b = in.read()) != -1) {   
              out.write(b);   
          }   
          in.close();   
      }   
  }   
  
  /**  
   * 解压静态方法  
   * @param zipFileName  
   * @param outputDirectory  
   * @throws Exception  
   */  
  public static void unzip(String zipFileName,String outputDirectory) throws Exception{   
      try {   
          org.apache.tools.zip.ZipFile zipFile = new org.apache.tools.zip.ZipFile(zipFileName);   
          java.util.Enumeration e = zipFile.getEntries();   

          org.apache.tools.zip.ZipEntry zipEntry = null;   

          while (e.hasMoreElements()){   
              zipEntry = (org.apache.tools.zip.ZipEntry)e.nextElement();    
              if (zipEntry.isDirectory()){   
                  String name=zipEntry.getName();   
                  name=name.substring(0,name.length()-1);        
                  mkDirs(outputDirectory+File.separator+name);                       
                  //System.out.println("创建目录："+outputDirectory+File.separator+name);   

              }else{   
                  String name=zipEntry.getName();   
                  String dir = name.substring(0,name.lastIndexOf("/"));   
                  mkDirs(outputDirectory+File.separator+dir);                    
                //System.out.println("创建文件："+outputDirectory+File.separator+name);                    
                  File f=new File(outputDirectory+File.separator+zipEntry.getName());   
                  f.createNewFile();   
                  InputStream in = zipFile.getInputStream(zipEntry);   
                  FileOutputStream out=new FileOutputStream(f);                      
                  int c;   
                  byte[] by=new byte[1024];   
                  while((c=in.read(by)) != -1){   
                      out.write(by,0,c);   
                  }   
                  out.close();   
                  in.close();   
              }   
          }   
      }   
      catch (Exception ex){   
      	ExceptionLogger.exception(ex);
      }   
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


  public static void main(String[] temp) {   
  	 String inputFileName = "D:\\工具\\astyle";    //你要压缩的文件夹   
     String zipFileName = "D:\\工具\\astyle.zip";  //压缩后的zip文件    
      try {   
      	//ZipUtil.zip(inputFileName, zipFileName);   
      	ZipUtil.unzip(zipFileName,inputFileName);
      	System.out.println("----");
      } catch (Exception ex) {   
          ex.printStackTrace();   
      }   
  }   

}
