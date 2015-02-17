package com.easy.persistance.easysql;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.easy.kit.utils.file.FileUtil;
/**
 * 
 * 加载SQL，放入内存
 *
 */
public class SqlFileReader {

	private static SqlMap sqlMap = new SqlMap();

	public static void readFile(String fileName) {
		//加载文件
		String fileContent = FileUtil.loadfromfile(fileName, "utf-8", FileUtil._KB);
		//删除换行符，注释
		if (fileContent != null) {
			Pattern p = Pattern.compile("\t|\r|\n|--.+?--");
			Matcher m = p.matcher(fileContent);
			fileContent = m.replaceAll("");
		}
		//匹配SQL
		Pattern pattern = Pattern.compile(".+?;");
		Matcher matcher = pattern.matcher(fileContent);
		//将SQL放入Map
		while (matcher.find()) {
			String m = matcher.group();
			System.out.println(m);
			String[] strArr = m.split(":");
			
			sqlMap.put(strArr[0].trim(), getSql(strArr[1].trim()));
		}
	}
	/**
	 * 将SQL里@{}替换成?
	 * @param sql
	 * @return
	 */
	public static String getSql(String sql){
		Pattern pattern = Pattern.compile("@\\{\\w+\\}");
		Matcher matcher = pattern.matcher(sql);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
		    matcher.appendReplacement(sb, "?");
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
	public static SqlMap getSqlMap(){
		return sqlMap;
	}
}
