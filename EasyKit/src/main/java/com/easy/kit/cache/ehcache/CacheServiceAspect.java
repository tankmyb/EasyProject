package com.easy.kit.cache.ehcache;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.cache.annotation.Cacheable;

/**
 * 缓存切面，方便使用{@link Cacheable}和{@link CacheEvit}批注。
 *
 * @author 唐竹
 * @date 创建时间：2011-9-30 下午04:52:00 
 */
@Aspect
public class CacheServiceAspect extends AbstractCacheAspect {

	@Pointcut("execution(@com.easy.kit.cache.ehcache.CacheEvictEx * *.*(..))")
	public void CacheEvictJoinPoint() {
	}

	@Pointcut("execution(@com.easy.kit.cache.ehcache.CacheableEx * *.*(..))")
	public void CacheableJoinPoint() {
	}

	@Around("CacheableJoinPoint() && @annotation(cacheable) ")
	public Object CacheableAdvice(ProceedingJoinPoint pjp, CacheableEx cacheable) throws Throwable {
		return super.CacheableAdvice(pjp, cacheable);
	} 

	@After("CacheEvictJoinPoint() &&  @annotation(cacheEvict)")
	public void CacheEvitAdvice(JoinPoint joinPoint, CacheEvictEx cacheEvict){
		super.CacheEvitAdvice(joinPoint, cacheEvict); 
	}

}
