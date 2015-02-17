package com.easy.kit.utils.cls;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.easy.kit.utils.log.ExceptionLogger;

public class ClassUtil {
	/**
	 * <方法描述> 获取当前class对应顶级泛型类
	 * 
	 * @param clazz
	 *            当前Class
	 * @return 对应的泛型类
	 */
	@SuppressWarnings( { "unchecked" })
	public static <T> Class<T> getSuperClassGenricType(final Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	@SuppressWarnings("unchecked")
	public static Class getSuperClassGenricType(final Class clazz, final int index) {
		Type genType = clazz.getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class) params[index];
	}

	public static <T> List<T> getStaticFieldByType(final Class clazz, final Class<T> type) {
		Field[] fields = clazz.getFields();
		List<T> list = new ArrayList<T>();
		try {
			for (Field field : fields) {
				if (((field.getModifiers() & Modifier.STATIC) != 0) && type.equals(field.getType())) {
					list.add((T) field.get(type));
				}
			}
		} catch (Exception e) {
			ExceptionLogger.exception(e);
		}
		return list;
	}

	/**
	 * <方法描述> 判断对象是否包含有相应类型的字段
	 * 
	 * @param clazz
	 * @param type
	 * @return
	 */
	public static Set<String> getFieldNameByType(final Class clazz, final Class type) {
		Set<String> fieldNames = new HashSet<String>();
		for (Field field : clazz.getDeclaredFields()) {
			if (field.getType().equals(type)) {
				fieldNames.add(field.getName());
			}
		}
		return fieldNames;
	}

	

	/**
	 * <方法描述>判断一个类是否是另一个接口的实现
	 * 
	 * @param interfaceClass
	 *            接口类
	 * @param implementsClass
	 *            实现类
	 * @return
	 */
	public static boolean isInterfaceOf(Class<?> interfaceClass, Class<?> implementsClass) {
		boolean result = false;
		if (implementsClass != null) {
			Class<?>[] types = implementsClass.getInterfaces();
			for (Class<?> cls : types) {
				if (cls.equals(interfaceClass)) {
					result = true;
					break;
				}
				result = isInterfaceOf(interfaceClass, cls.getSuperclass());
				if (result)
					break;
			}
		}
		return result;
	}

}
