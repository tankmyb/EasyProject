package com.easy.persistance.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.easy.kit.utils.Tools;
import com.easy.persistance.orm.SuperRow;

public class MapUtil {

	/**
	 * 比较两个Map，将key相同，value不同的区分出来
	 * 
	 * @param oldMap
	 * @param newMap
	 * @return
	 */
	public static Map<String, Object> compareMap(Map<String, Object> oldMap,
			Map<String, Object> newMap) {
		Map<String, Object> updateMap = new HashMap<String, Object>();
		Set<String> newKeySet = newMap.keySet();
		for (String key : newKeySet) {
			Object newValue = newMap.get(key);
			if (!Tools.eq(newMap.get(key), oldMap.get(key))) {
				updateMap.put(key, newValue);
			}
		}
		return updateMap;
	}

	public static <T> T convertMap(Class<T> type, Map<String, Object> map) {
		// 排除SuperRow类里的属性
		BeanInfo beanInfo = null;
		T obj = null;
		try {
			// 获取类属性
			beanInfo = Introspector.getBeanInfo(type, SuperRow.class);
			// 创建 JavaBean 对象
			obj = type.newInstance();
			// 给 JavaBean 对象的属性赋值
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			int len = propertyDescriptors.length;
			for (int i = 0; i < len; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = ColumnFormatUtil.underLineName(descriptor
						.getName());
				System.out.println(propertyName);
				if (map.containsKey(propertyName)) {
					Object value = map.get(propertyName);
					descriptor.getWriteMethod().invoke(obj, value);

				}
			}
		} catch (IntrospectionException e) {
			throw new RuntimeException();
		} catch (InstantiationException e) {
			throw new RuntimeException();
		} catch (IllegalAccessException e) {
			throw new RuntimeException();
		} catch (InvocationTargetException e) {
			throw new RuntimeException();
		}
		return obj;
	}
}
