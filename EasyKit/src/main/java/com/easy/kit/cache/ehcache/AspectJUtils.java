package com.easy.kit.cache.ehcache;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * 
 * 在您的切面中调用该类的静态方法，可以获得方法，参数、类名等信息。
 *
 * @author:
 *		myb
 *
 *
 */

public class AspectJUtils {
	/**
	 * @Title: 
	 *		getMethodName 
	 * @Description: 
	 *		获得方法名称
	 * @param 
	 *      @param joinPoint
	 * @return 
	 *		String
	 */
	public static String getMethodName(JoinPoint joinPoint) {
		return joinPoint.getStaticPart().getSignature().getName();
	}

	/**
	 * @Title: 
	 *		getMetthodAnnotation 
	 * @Description: 
	 *		获得方法的批注
	 * @param 
	 *      @param joinPoint
	 *      @param annotationClass
	 * @return 
	 *		Annotation
	 */
	@SuppressWarnings("unchecked")
	public static Annotation getMetthodAnnotation(JoinPoint joinPoint, Class annotationClass) {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getStaticPart().getSignature();
		Method method = methodSignature.getMethod();
		return method.getAnnotation(annotationClass);
	}

	/**
	 * 
	 * @Title: 
	 *		getMethod 
	 * @Description: 
	 *		获得方法对象
	 * @param 
	 *      @param joinPoint
	 * @return 
	 *		Method
	 */
	public static Method getMethod(JoinPoint joinPoint) {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getStaticPart().getSignature();
		Method method = methodSignature.getMethod();
		return method;
	}

	/**
	 * 
	 * 	获得方法参数
	 *
	 *	@param 
	 *		joinPoint
	 *	@return
	 *		Object[]

	 */
	public static Object[] getMethodArgs(JoinPoint joinPoint) {
		return joinPoint.getArgs();
	}



	/**
	 * 
	 * 	获得一个与相应签名关联的成员
	 *	@param joinPoint
	 *	@return
	 *		Class<?>
	 */
	public static Class<?> getDeclaringType(JoinPoint joinPoint) {
		return joinPoint.getStaticPart().getSignature().getDeclaringType();
	}

	/**
	 * 
	 * 	返回与相应签名关联的成员的完全限定名称。从方法调用连接
	 * 	点上调用返回字符串实例如下：<br/>
	 * 	com.xyz.abc.Class
	 *
	 *	@param joinPoint
	 *	@return
	 *		String
	 *
	 * 	@throws 
	 *		
	 */
	public static String getDeclaringTypeName(JoinPoint joinPoint) {
		return joinPoint.getStaticPart().getSignature().getDeclaringTypeName();
	}
	public static void main(String[] args) {
	}
}