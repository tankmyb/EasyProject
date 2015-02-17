package com.java.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
public class ZipFile {

	 String algorithm = "DES";
	 String rawKeyData = "Blowfish";
	 Cipher cipher = null;
	 
	 /**
	  * 要压缩的文件或文件夹
	  */
	 File input;
	 
	 /**
	  * 压缩后生成的压缩文件
	  */
	 FileOutputStream output;
	 
	 public ZipFile(String rawKeyData){
	  this.rawKeyData = rawKeyData;
	 }
	 
	 public String beforeZip(String filepath) throws Exception{
	  input = new File(filepath);
	  String zipfile = input.getParent() + "/" + input.getName() + ".zip";
	  output = new FileOutputStream(zipfile);
	  //zip(input, output);
	  return zipfile;
	 }
	 
	 public String beforeZip(String filepath, String outputpath) throws Exception{
	  input = new File(filepath);
	  String zipfile = outputpath + "/" + input.getName() + ".zip";
	  output = new FileOutputStream(zipfile);
	  return zipfile;
	 }
	 
	 public void zip(boolean delInputFile) throws Exception{
	  zip(input, output);
	  if(delInputFile){
	   delFile(input);
	  }
	 }
	 
	 public void delFile(File input) throws Exception{
	  if(input.isDirectory())
	  {
	   File[] files = input.listFiles();
	   if(files.length>1){
	    for(int i=0;i<files.length;i++){
	     if(files[i].isDirectory()){
	      delFile(files[i]);
	     }
	     else{
	      files[i].delete();
	     }
	    }
	    input.delete();
	   }
	  }
	  else
	  {
	   input.delete();
	  }
	 }
	 
	 public void zip(String filepath, String zipfile) throws Exception{
	  File input = new File(filepath);
	  FileOutputStream output = new FileOutputStream(zipfile);
	  zip(input, output);
	 }
	 
	 /**
	     * 压缩文件
	     * @param input 被压缩的文件或者文件夹
	     * @param output 输出的压缩文件
	     * @throws IOException
	     */
	    public void zip(File input, OutputStream output) throws Exception{
	     ZipOutputStream zipOutput = new ZipOutputStream(output);
	        if (input.isDirectory())
	        {
	            zipDirectory(zipOutput, "", input);
	        }
	        else
	        {
	            zipFile(zipOutput, "", input);
	        }       
	        zipOutput.close();
	    }
	    /**
	     * 压缩文件
	     * @param zipOutput
	     * @param path
	     * @param file
	     * @throws IOException
	     */
	    private void zipFile(ZipOutputStream zipOutput, String path, File file) throws Exception
	    {
	        ZipEntry zipEntry = new ZipEntry((path.length() == 0 ? "" : path + "/") + file.getName());
	        zipOutput.putNextEntry(zipEntry);
	        FileInputStream ins = new FileInputStream(file);
	        byte[] data = new byte[ins.available()];
	        ins.read(data);
	        zipOutput.write(cipher.doFinal(data));
	        ins.close();       
	        zipOutput.closeEntry();
	    }
	   
	    /**
	     * 压缩文件夹
	     * @param zipOutput
	     * @param path
	     * @param input
	     * @throws IOException
	     */
	    private void zipDirectory(ZipOutputStream zipOutput, String path, File input)
	    throws Exception
	    {
	        File[] subFiles = input.listFiles();
	        for (int i = 0; i < subFiles.length; i++)
	        {
	            if (subFiles[i].isFile())
	            {
	    zipFile(zipOutput,(path.length() == 0 ? "" : path + "/") + input.getName(), subFiles[i]);
	   }
	   else
	   {
	                zipDirectory(zipOutput, (path.length() == 0 ? "" : path+ "/")+ input.getName(), subFiles[i]);
	   }
	        }
	    }
	   
	    /**
	     * 解压缩
	     * @param input 压缩文件
	     * @param output 解压位置
	     * @throws IOException
	     */
	    public void unzip(String zipfile, String unzippath) throws Exception
	    {
	     File infile = new File(zipfile);
	     FileInputStream fins = new FileInputStream(infile);
	     ZipInputStream zipIns = new ZipInputStream(fins);
	        ZipEntry zipEntry = zipIns.getNextEntry();
	        File output = null;
	        if(unzippath == null ){
	         unzippath = infile.getPath();
	        }
	        output = new File(unzippath);
	        while (zipEntry != null)
	        {
	            File file = new File(output, zipEntry.getName());
	            file.getParentFile().mkdirs();
	            FileOutputStream ous = new FileOutputStream(file);
	            List datalist = new ArrayList();
	            byte[] tmp = new byte[1024];
	            int len = 0, length = 0;
	            while ((len = zipIns.read(tmp)) != -1)
	            {
	             length += len;
	          byte[] tmp2 = new byte[len];
	          System.arraycopy(tmp, 0, tmp2, 0, len);
	             datalist.add(tmp2);            }
	            byte[] data = new byte[length];
	            len = 0;
	            for(int i=0;i<datalist.size();i++){
	             tmp = (byte[])datalist.get(i);
	             len += tmp.length;
	             System.arraycopy(tmp, 0, data, len, tmp.length);
	            }
	            data = cipher.doFinal(data);
	            ous.write(data);
	            ous.close();
	            zipIns.closeEntry();
	            zipEntry = zipIns.getNextEntry();
	        }
	        zipIns.close();
	    }
	   
	    /**
	     * des: 初始化加密Cipher对象
	     * <br>tables:
	     * @throws Exception
	     */
	    public void enCipher() throws Exception{
	     SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
	     DESKeySpec keySpec = new DESKeySpec(rawKeyData.getBytes());  
	     SecretKey secretKey = keyFactory.generateSecret(keySpec);
	  cipher = Cipher.getInstance(algorithm);
	  cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	    }
	   
	    /**
	     * des: 初始化解密Cipher对象
	     * <br>tables:
	     * @throws Exception
	     */
	    public void deCipher() throws Exception{
	     SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
	     DESKeySpec keySpec = new DESKeySpec(rawKeyData.getBytes());
	     SecretKey secretKey = keyFactory.generateSecret(keySpec);
	  cipher = Cipher.getInstance(algorithm);
	     cipher.init(Cipher.DECRYPT_MODE, secretKey);
	    }
	   
	    /**
	     * des: 测试
	     * <br>tables:
	     * @param args
	     * @throws Exception
	     */
	    public static void main(String[] args) throws Exception
	    {
	     ZipFile zipencoder = new ZipFile("zhoushil");
	     //加密的方法
	     //  zipencoder.enCipher();
	     //加密，加压时传参数方式一：
	     //对文件夹
	        //zipencoder.zip(new File("D://traceSys"), new FileOutputStream("D://traceSys.zip"));
	     //对单个文件
	      //zipencoder.zip(new File("d:/2012-2-24.sql"), new FileOutputStream("d:/sql.zip"));
	     //加密，加压时传参数方式二：（这个是能过删除原来的文件和文件夹的 zipencoder.zip(true)方法）
	     //对文件夹
	      //zipencoder.beforeZip("D://mm");
	     //对单个文件
	      //zipencoder.beforeZip("E:/tmp/rcvdata/exp002/新建 文本文档 (6).txt");
	     //删除加密，压缩的文件或者文件夹
	    // zipencoder.zip(true);
	     
	     
	     
	     //解密，加压
	     //解密
	          zipencoder.deCipher();
	     //解压整个文件夹
	       zipencoder.unzip("D://mm.zip", "D://mm");
	     // 解压单个文件 
	      //zipencoder.unzip("E:/tmp/rcvdata/a.zip", "E:/tmp/rcvdata");
	     
	    }
}
