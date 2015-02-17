package com.easy.persistance.util;

import java.util.Date;

import com.easy.kit.utils.datetime.DateTimeUtil;
import com.easy.persistance.common.SQLAssistant;

public class DataTypeUtil {
	/**
	 * 根據數據庫字段的類型,得到java相應的類型
	 * 
	 * @param columnType
	 * @return
	 * @throws DataTypeUndefinedException
	 */
	public static String getJavaDataType(String columnType) throws RuntimeException {
		columnType = columnType.toLowerCase();
		if (columnType.startsWith("varchar")) {
			return "String";
		} else if (columnType.equals("nvarchar")) {
			return "String";
		} else if (columnType.equals("text")) {
			return "String";
		} else if (columnType.equals("char")) {
			return "String";
		}

		else if (columnType.equals("bit")) {
			return "Boolean";
		} else if (columnType.equals("bool")) {
			return "Boolean";
		}

		else if (columnType.equals("smallint")) {
			return "Integer";
		} else if (columnType.equals("int")) {
			return "Integer";
		} else if (columnType.equals("int identity")) {
			return "Integer";
		} else if (columnType.equals("integer")) {
			return "Integer";
		} else if (columnType.equals("int4")) {
			return "Integer";
		} else if (columnType.equals("serial")) {
			return "Integer";
		}else if(columnType.equals("number")){
			return "Integer";
		}else if(columnType.startsWith("numeric")){
			return "Integer";
		}

		else if (columnType.equals("short")) {
			return "Short";
		}

		else if (columnType.equals("long")) {
			return "Long";
		}

		else if (columnType.equals("float")) {
			return "Double";
		} else if (columnType.equals("double")) {
			return "Double";
		} else if (columnType.equals("float8")) {
			return "Double";
		}

		else if (columnType.equals("smalldatetime")) {
			return "Date";
		} else if (columnType.equals("datetime")) {
			return "Date";
		} else if (columnType.equalsIgnoreCase("date")) {
			return "Date";
		} else if (columnType.equalsIgnoreCase("timestamp")) {
			return "Date";
		}
		throw new RuntimeException("沒有此類型:" + columnType);
	}

	/**
	 * 判断参数类型
	 */
	public static String judgeType(Object value, boolean isReplaceSpecialCharacter){
		String s = null;
		if (value == null)
			s = "null";
		else if (value instanceof String) {
			if (isReplaceSpecialCharacter) {
				value = replaceSpecialCharacter((String) value);
			}
			s = "'" + value + "'";
		} else if (value instanceof Boolean) {
			if (SQLAssistant.isPostgreSQLDatabase()) {
				s = ((Boolean) value).booleanValue() ? "true" : "false";
			} else {
				s = ((Boolean) value).booleanValue() ? "1" : "0";
			}
		} else if (value instanceof Number) {
			s = ((Number) value).toString();
		} else if (value instanceof java.sql.Date) {
			s = "'" + DateTimeUtil.getDateStr((Date) value) + "'";
		} else if (value instanceof java.util.Date) {
			s = "'" + DateTimeUtil.parseSQLTimestamp(DateTimeUtil.getDateTimeStr((java.util.Date) value)) + "'";
		} else {
			s = value.toString();
		}
		return s;
	}

	/**
	 * 判断参数类型
	 */
	public static Object judgeJdbcType(Object obj) {
		Object bean = obj;
		if (obj instanceof Date) {
			bean = new java.sql.Timestamp(((Date) obj).getTime());
		} else if (obj instanceof Boolean) {
			if (!SQLAssistant.isPostgreSQLDatabase()) {
				if ((Boolean) obj == true) {
					bean = 1;
				} else {
					bean = 0;
				}
			}
		}
		return bean;
	}
	public static String replaceSpecialCharacter(String sql){
		if(SQLAssistant.isMssqlDatabase()){
			if(sql.indexOf("'")!=-1){
				sql=sql.replaceAll("'","''");
			}
		}else if(SQLAssistant.isMysqlDatabase()){
			if(sql.indexOf("'")!=-1){
				sql=sql.replace("'","\\'");
			}
		}
		return sql;
	}

}
