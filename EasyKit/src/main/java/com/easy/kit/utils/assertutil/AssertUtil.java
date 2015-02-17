package com.easy.kit.utils.assertutil;

import com.easy.kit.utils.Tools;

public class AssertUtil {

	/**
	 * 不为TURE,抛异常
	 */
	public static void isTrue(boolean expression){
		isTrue(expression,"this expression must be true");
	}
	/**
	 * 不为TURE,抛异常
	 */
	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw new IllegalArgumentException(message);
		}
	}
	/**
	 * 不为NULL,抛异常
	 */
	public static void isNull(Object object){
		isNull(object,"the object argument must be null");
	}
	/**
	 * 不为NULL,抛异常
	 */
	public static void isNull(Object object, String message) {
		if (object != null) {
			throw new IllegalArgumentException(message);
		}
	}
	/**
	 * 为NULL,抛异常
	 */
	public static void notNull(Object object) {
		notNull(object, "this argument is required; it must not be null");
	}
	/**
	 * 为NULL,抛异常
	 */
	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}
	/**
	 * 为空,抛异常
	 */
	public static void notBlank(Object obj) {
		notBlank(obj,"must not be blank");
	}
	/**
	 * 为空,抛异常
	 */
	public static void notBlank(Object obj, String message) {
		if (Tools.isBlank(obj)) {
			throw new IllegalArgumentException(message);
		}
	}
}
