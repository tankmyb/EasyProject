package com.easy.persistance.util;

import java.util.Iterator;
import java.util.List;

import com.easy.kit.utils.string.StringUtil;

public class PrintSqlUtil {
	/**
	 * 组装一条
	 * @param sql
	 * @param values
	 * @return
	 */
	public static String packageSql(String sql, List<Object> values) {
		StringBuffer sb = new StringBuffer();
		
		Object[] objArray = new Object[values.size()];
		int i = 0;
		for (Iterator<Object> it = values.iterator(); it.hasNext();) {
			Object obj = it.next();
			objArray[i++] = DataTypeUtil.judgeType(obj, true);
		}
		sb.append(StringUtil.replace(sql, objArray));
		return sb.toString();
	}
	/**
	 * 组装一批SQL
	 * @param sql
	 * @param valuesList
	 * @return
	 */
	public static String packageSqlList(String sql, List<List<Object>> valuesList) {
		StringBuffer sb = new StringBuffer();
		for(List<Object> values:valuesList){
			Object[] objArray = new Object[values.size()];
			int i = 0;
			for (Iterator<Object> it = values.iterator(); it.hasNext();) {
				objArray[i++] = DataTypeUtil.judgeType(it.next(), true);
			}
			sb.append(StringUtil.replace(sql, objArray)).append("\\n");
		}
		return sb.toString();
	}
}
