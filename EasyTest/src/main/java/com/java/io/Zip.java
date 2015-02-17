package com.java.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Date;

import javax.crypto.Cipher;


public class Zip {
	public static void zip()throws Exception{
		Date start = new Date();
		String keyfile = "d:\\pk.key";
		SecureRandom sr = new SecureRandom();
	  KeyPairGenerator kg = KeyPairGenerator.getInstance("RSA");
	  kg.initialize(512, sr);
	  //产生新密钥对
	  KeyPair kp = kg.generateKeyPair();
	  //获得私匙
	  PrivateKey privateKey = kp.getPrivate();
	  //获得公钥
	  PublicKey publicKey = kp.getPublic();
	  File f = new File(keyfile);
	  f.createNewFile();
	  FileOutputStream fos = new FileOutputStream(f);
	  ObjectOutputStream dos = new ObjectOutputStream(fos);
	  dos.writeObject(publicKey);
	  
	  ZipUtil.ZipSubdirectory(new File("d:\\traceSys"));
	  Cipher cipher = Cipher.getInstance("RSA");
	  cipher.init(Cipher.ENCRYPT_MODE, privateKey);
	  FileInputStream fis = new FileInputStream("d:\\traceSys.zip");
	  FileOutputStream fos1 = new FileOutputStream("d:\\traceSysRAS.zip");
	  byte[] b = new byte[53];
	  while (fis.read(b) != -1) {
	   fos1.write(cipher.doFinal(b));
	  }
	  fos.close();
	  dos.close();
	  
	  fis.close();
	  fos1.close();
	  System.out.println(new Date().getTime() - start.getTime());
	
	}
	public static void unzip() throws Exception{
		Date start = new Date();
		String keyfile = "d:\\pk.key";
		Key publicKey = ZipEncrypt.getKey(keyfile);
		Cipher cipher = Cipher.getInstance("RSA");
	  cipher.init(Cipher.DECRYPT_MODE, publicKey);
	  FileInputStream fis = new FileInputStream("d:\\traceSysRAS.zip");
	  FileOutputStream fos = new FileOutputStream("d:\\traceSysRAS1.zip");
	  byte[] b = new byte[64];
	  while (fis.read(b) != -1) {
	   fos.write(cipher.doFinal(b));
	  }
	  fos.close();
	  fis.close();
	  ZipUtil.unZip("d://n","d:\\traceSysRAS1.zip");
	  System.out.println(new Date().getTime() - start.getTime());
	}
	/**
	  * 见文件夹打包成tar
	  * @param outfile
	  * @param path
	  * @param deleteSrc
	  * @return
	  */
	 public  static int compressTarFolder(String inpath,String outfile,boolean deleteSrc) {
	  // 创建压缩文件
	  if(outfile == null || outfile.trim().length() ==0)
	  {
	   return -1;
	  }
	  if(inpath == null || inpath.trim().length() ==0)
	  {
	   return -1;
	  }
	  String operatsys = System.getProperties().getProperty("os.name").toLowerCase();
	  //7z源文件路径
	  if (operatsys==null && "".equals(operatsys))
	   operatsys="window";
	  String cmd;
	  if (operatsys.indexOf("window")>=0) 
	  {
	   cmd = "\""+"D:\\Program Files\\7-Zip\\7z.exe\" a -tzip "+outfile+" "+inpath + "\\* -r -p123";
	  }
	  else
	  {
	   cmd = "zip -r "+ outfile +" "+inpath;
	  }
	  int ret = executeCommand(cmd);
	  if(ret != 0)
	  {
	  }
	  //如果可以删除则删除源文件
	  if(deleteSrc)
	  {
	   if (operatsys.indexOf("window")>=0)
	   {
	    File f = new File(inpath);
	    if(f.isDirectory())
	    {
	     for(File file :f.listFiles())
	     {
	      file.delete();
	     }
	     f.delete();
	    }
	    
	   }
	  }
	  return ret;
	 }
	 public static int executeCommand(String cmd) {

		  
		  int ret = 0 ;
		  try 
		  {
		   String line;
		   Process process = Runtime.getRuntime().exec(cmd);
		   BufferedReader bufferedReader = new BufferedReader ( new InputStreamReader(process.getInputStream()));
		   while ( (line = bufferedReader.readLine()) != null)
		    {
		     System.out.println(line);
		    }
		   process.waitFor(); 
		   ret = process.exitValue();
		  } 
		  catch (Exception e) 
		  {
		   e.printStackTrace();
		   ret = -1;
		  }
		  return ret;
		 }
		 
	public static void main(String[] args)  throws Exception{
		//Zip.unzip();
		Zip.compressTarFolder("d:\\traceSys", "d:\\traceSys.zip", false);
		
		Zip.compressTarFolder("/home/chinawidth/run/codes2012-02-29-02-38-055", "/home/chinawidth/run/codes2012-02-29-02-38-055/aa.zip", false);
	}
}
