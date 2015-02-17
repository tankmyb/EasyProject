package com.easy.kit.cache.ehcache;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.apache.commons.lang.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public abstract class AbstractCacheAspect {
	
	/**
	 * 子类定义CacheableJoinPoint通知，该通知类型必须是Around通知。 Note:<br>
	 * <li>从缓存找到缓存对象，直接返回该对象</li> <li>
	 * 从缓存没有找到缓存对象，调用目标方法，并将其返回值（不为null）保存到缓存中</li>
	 * 
	 * @param pjp
	 *          - 切入点
	 * @param cacheable
	 *          - {@link CacheableEx}批注
	 * @throws Throwable
	 *           - 被修饰的方法可能抛出的异常，这种异常这里不用处理。
	 */
	public Object CacheableAdvice(ProceedingJoinPoint pjp, CacheableEx cacheable)
			throws Throwable {
		Method method = AspectJUtils.getMethod(pjp);
		Object[] args = AspectJUtils.getMethodArgs(pjp);
		String key = cacheable.key() + getKeyPostfix(method, args);
		System.out.println(key+"==");
		Object cachedObject = CacheFactory.get(key);
		if (cachedObject == null) {
			cachedObject = pjp.proceed();
			if (cachedObject != null) {
				CacheFactory.put(key, cachedObject);
			}
		}
		return cachedObject;
	}

	/**
	 * 子类定义CacheEvitJoinPoint通知，该通知类型必须是After通知。 Note:<br>
	 * <li>删除缓存</li> <li>
	 * 
	 * @param pjp
	 *          - 切入点
	 * @param cacheable
	 *          - {@link CacheEvictEx}批注
	 */
	public void CacheEvitAdvice(JoinPoint pjp, CacheEvictEx cacheable) {
		Method method = AspectJUtils.getMethod(pjp);
		Object[] args = AspectJUtils.getMethodArgs(pjp);
		String key = cacheable.key() + getKeyPostfix(method, args);
		CacheFactory.remove(key);
	}

	private String getKeyPostfix(Method method, Object[] args) {
		Annotation[][] annotations = method.getParameterAnnotations();
		StringBuffer keyPostfix = new StringBuffer("");
		if (ArrayUtils.isNotEmpty(args)) {
			for (int argsIndex = 0, len = args.length; argsIndex < len; argsIndex++) {
				for (int annotationIndex = 0; annotationIndex < annotations[argsIndex].length; annotationIndex++) {
					Annotation annotation = annotations[argsIndex][annotationIndex];
					Class<? extends Annotation> targetClass = annotation.annotationType();
					// 批注类型相同
					if (targetClass == CacheKeyEx.class) {
						CacheKeyEx cacheKeyEx = (CacheKeyEx) annotation;
						String value = cacheKeyEx.value();
						if (org.apache.commons.lang.StringUtils.isNotBlank(value)) {
							Object obj = ReflectUtil.getPropertyValue(args[argsIndex], value);
							keyPostfix.append("_").append(obj);
						} else {
							keyPostfix.append("_").append(args[argsIndex]);
						}
					}
				}
			}
		}
		return keyPostfix.toString();
	}
}
