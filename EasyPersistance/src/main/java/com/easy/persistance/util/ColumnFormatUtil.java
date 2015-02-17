package com.easy.persistance.util;

import java.util.regex.Pattern;

public class ColumnFormatUtil {

	
	/**
	 * 格式化列名,将下划线改成大写,如果 isUpperCaseFirstChart = true 时,开头字母也要大写
	 */
	public static String upperCaseName(String name, boolean isUpperCaseFirstChart) {
		if(name==null){
			return "";
		}
		name =name.trim().toLowerCase();
		StringBuffer sb = new StringBuffer("");
		String firstLetter;
		String regEx = "_";
		Pattern p = Pattern.compile(regEx);
		String[] r = p.split(name);
		for (int i = 0; i < r.length; i++) {
			firstLetter = r[i].substring(0, 1);
			if (i == 0 && !isUpperCaseFirstChart) {
				sb.append(firstLetter.toLowerCase() + r[i].substring(1));
			} else {
				sb.append(firstLetter.toUpperCase() + r[i].substring(1));
			}
		}
		return sb.toString();

	}
	/**
	 * 格式化列名,将大写改成下划线
	 */
	public static String underLineName(String name) {
		StringBuffer sb = new StringBuffer("");
		char ch;
		for (int i = 0; i < name.length(); i++) {
			ch = name.charAt(i);
			if (Character.isUpperCase(ch)) {
				if(i>0){
					sb.append("_");
				}
				sb.append(Character.toLowerCase(ch));
			} else {
				sb.append(ch);
			}
		}
		return sb.toString();
	}
	public static String getTableAlias(String tableName) {
		StringBuffer tableAlias = null;
			char ch;
			boolean isUnderLine = false;
			String firstLetter = tableName.substring(0, 1);
			tableAlias = new StringBuffer(firstLetter.toLowerCase());
			for (int i = 1; i < tableName.length(); i++) {
				ch = tableName.charAt(i);
				if (isUnderLine) {
					tableAlias.append(Character.toLowerCase(ch));
					isUnderLine = false;
				}
				/*if (Character.isUpperCase(ch)) {

					tableAlias.append(Character.toLowerCase(ch));
				}*/
				if (ch == '_') {
					isUnderLine = true;
				}
			}
		
		return tableAlias.toString();
	}

}
