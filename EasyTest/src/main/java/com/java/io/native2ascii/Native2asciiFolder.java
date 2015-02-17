package com.java.io.native2ascii;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.easy.kit.utils.file.FileUtil;
import com.easy.kit.utils.file.FolderUtil;

public class Native2asciiFolder {

	private static final String java_path = "D://Program Files//Java//jdk1.6.0_30";
	private static final String src_floder = "D://boss//src";
	private static final String target_folder = "D://boss//target";

	public static void native2asciiFolder(List list){
		for(java.util.Iterator it = list.iterator();it.hasNext();){
			Object obj = it.next();
			if(obj instanceof String) {
				String srcPath = (String)obj;
				srcPath=srcPath.replaceAll("\\\\", "/").trim();
				String targetPath = srcPath.replace(src_floder, target_folder);
			    FileUtil.createFolder(targetPath, false);
			    native2asciiFile(srcPath,targetPath);
		    }else if(obj instanceof List) {
		    	native2asciiFolder((List)obj);
		    }
		}
	}
	public static void native2asciiFile(String srcPath,String targetPath) {
		try {
			System.out.println("Begin to execute...");
			Runtime.getRuntime().exec(
					java_path + "//bin//native2ascii.exe -reverse "  + srcPath + " " + targetPath);
			System.out.println("End");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String arg[]) {
		List list = FolderUtil.fileList(null, new File(src_floder), src_floder);
		Native2asciiFolder.native2asciiFolder(list);
	}
}
