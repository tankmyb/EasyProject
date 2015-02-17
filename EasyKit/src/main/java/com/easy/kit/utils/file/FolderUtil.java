package com.easy.kit.utils.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.easy.kit.utils.Tools;


public class FolderUtil{
	public static String getUrlChar()
	{
		return System.getProperties().getProperty("file.separator");
	}
	public static List<Object> fileList(List<Object> list, File file, String root)
	{
		if (list == null)
		{
			list = new ArrayList<Object>();
		}
		String[] filelist = file.list();
		for (int i = 0; i < filelist.length; i++)
		{
			if(Tools.isValid(root)){
				root = root+getUrlChar();
			}
			File delfile = new File(root  + filelist[i]);
			if (!delfile.isDirectory())
			{
				list.add(delfile.getPath());
			} else
			{
				list = fileList(list, delfile, delfile.getPath());
			}
		}
		return list;
	}
	/**
	 * 得到指定文件夹中的文件列表
	 * 
	 * @param folderPath
	 *            String 文件夹
	 * @return String[] 文件列表
	 */
	public static String[] getFileListOfFolder(String folderPath)
	{
		String[] fileList = null;
		File file = new File(folderPath);
		if (file.isDirectory())
		{
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++)
			{
				fileList[i] = folderPath + getUrlChar() + fileList[i];
			}
		}
		return fileList;
	}
	public static void output(List list){
		for(java.util.Iterator it = list.iterator();it.hasNext();){
			Object obj = it.next();
			if(obj instanceof String) {
			  System.out.println(obj);
		    }else if(obj instanceof List) {
		    	output((List)obj);
		    }
		}
	}
	public static void main(String[] args) {
		List list = FolderUtil.fileList(null, new File("D:\\aa"), "D:\\aa");
		FolderUtil.output(list);
		System.out.println(list.size());
	}
}
