package com.easy.kit.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Date;

import com.easy.kit.utils.string.ArrayUtil;

public class Tools {

	/**
	 * 可以判断是否NULL
	 * 字符串是否长度为0
	 * Number类是否为0
	 * 数组长度是否为0
	 * Collection长度是否为0
	 * @param value
	 * @return
	 */
	public static boolean isBlank(Object value) {
		if (value == null)
			return true;
		if (value instanceof CharSequence) {
			return ((CharSequence) value).length() == 0;
		} else if (value instanceof Number) {
			return ((Number) value).doubleValue() == 0;
		} else if (value.getClass().isArray()) {
			return ((Object[]) value).length == 0;
		} else if (value instanceof Collection) {
			return ((Collection) value).isEmpty();
		} else {
			return value.toString().length() == 0;
		}
	}

	public static boolean isValid(Object value) {
		return !isBlank(value);
	}

	/**
	 * 不同类型的两个值比较是否相等
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static boolean eq(Object o1, Object o2) {
		if (o1 != null && o2 != null && !o1.getClass().equals(o2.getClass())) {
			o2 = Tools.valueOf(o1.getClass(), o2);
		}
		return isEqual(o1, o2);
	}

	/**
	 * 相同类型的两个值比较是否相等
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static boolean isEqual(Object o1, Object o2) {
		if (o1 == o2)
			return true;
		if (o1 != null)
			return o1.equals(o2);
		if (o2 != null)
			return o2.equals(o1);
		return false;
	}

	/**
	 * 将 value 转化成 cls 相同类型
	 * @param cls
	 * @param value
	 * @return
	 */
	public static Object valueOf(Class cls, Object value) {
		try {
			if (cls == null || value == null) {
				return value;
			} else if (cls.isPrimitive()) {
				return valueOfPrimitive(cls, value);
			} else if (value instanceof Date) {
				if (cls.equals(java.sql.Timestamp.class)) {
					return new java.sql.Timestamp(((Date) value).getTime());
				} else if (cls.equals(java.sql.Date.class)) {
					return new java.sql.Date(((Date) value).getTime());
				} else if (cls.equals(java.util.Date.class)) {
					return new java.util.Date(((Date) value).getTime());
				} else {
					Constructor c = cls
							.getConstructor(new Class[] { long.class });
					return c.newInstance(new Object[] { ((Date) value)
							.getTime() });
				}
			}
			if (cls.equals(value.getClass())) {
				return value;
			}
			if ((value instanceof String) && ((String) value).length() == 0) {
				value = ""; // settle new BigDecimal(""),new Long("0");
			}
			if ((cls.equals(Boolean.class))
					&& ArrayUtil.contains(new String[] { "1", "是", "Y", "yes",
							"true" }, value.toString())) {
				return Boolean.TRUE;
			} else if ((cls.equals(Boolean.class))
					&& ArrayUtil.contains(new String[] { "0", "否", "N", "no",
							"false" }, value.toString())) {
				return Boolean.FALSE;
			}
		} catch (Exception e) {
			throw new RuntimeException(e.toString() + ",class:" + cls.getName()
					+ ",value:" + value);
		}
		return null;
	}

	public static Object valueOfPrimitive(Class cls, Object value) {
		if (value == null)
			value = "0";
		String s = value.toString();
		if (s.length() == 0)
			s = "0";
		if (cls.equals(short.class))
			return new Short(s);
		else if (cls.equals(int.class))
			return new Integer(s);
		else if (cls.equals(long.class))
			return new Long(s);
		else if (cls.equals(double.class))
			return new Double(s);
		else if (cls.equals(boolean.class)) {
			return isTrue(s);
		} else
			throw new RuntimeException("can not get the Object by class:"
					+ cls.getName() + " from:" + value);
	}

	public static boolean isTrue(String value) {
		if (value == null)
			return false;
		return ArrayUtil.contains(new String[] { "1", "是", "Y", "yes", "true",
				"可" }, value);
	}
	/**
	 * 是否包含对象
	 * @param array
	 * @param element
	 * @return
	 */
	public static boolean containsElement(Object[] array, Object element) {
		if (array == null) {
			return false;
		}
		for (int i = 0; i < array.length; i++) {
			if (eq(array[i], element)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 将对象添加到数组里，返回新数组
	 * @param array
	 * @param obj
	 * @return
	 */
	public static Object[] addObjectToArray(Object[] array, Object obj) {
		Class compType = Object.class;
		if (array != null) {
			compType = array.getClass().getComponentType();
		}
		else if (obj != null) {
			compType = obj.getClass();
		}
		int newArrLength = (array != null ? array.length + 1 : 1);
		Object[] newArr = (Object[]) Array.newInstance(compType, newArrLength);
		if (array != null) {
			System.arraycopy(array, 0, newArr, 0, array.length);
		}
		newArr[newArr.length - 1] = obj;
		return newArr;
	}
}
