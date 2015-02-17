package com.easy.kit.utils.aspectJ;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

public class AspectJUtil  {
	

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
	public static String getMethodName(JoinPoint joinPoint){
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
	public static Annotation getMetthodAnnotation(JoinPoint joinPoint,Class annotationClass){
		MethodSignature methodSignature = (MethodSignature)joinPoint.getStaticPart().getSignature();
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
	public static Method getMethod(JoinPoint joinPoint){
		MethodSignature methodSignature = (MethodSignature)joinPoint.getStaticPart().getSignature();
		Method method = methodSignature.getMethod();
		return method;
	}
	
	/**
	 * 
	 * @Title: 
	 *		getMethodArgs 
	 * @Description: 
	 *		获得方法参数
	 * @param 
	 *      @param joinPoint
	 * @return 
	 *		Object[]
	 */
	public static Object[] getMethodArgs(JoinPoint joinPoint){
		return joinPoint.getArgs();
	}
}
