package com.easy.kit.cache.memcache;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.easy.kit.cache.CacheableAnnotation;
import com.easy.kit.utils.aspectJ.AspectJUtil;

@Component
@Aspect
public class CacheableAspect {
	@Pointcut("execution(@com.easy.kit.cache.CacheableAnnation * *.*(..))")
	public void inCache() {
	}

	@Around("inCache()")  
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		String cacheKey = getCacheKey(pjp);
		System.out.println(cacheKey+"==cacheKey");
			IMemcacheHandler handler =  MemcacheHandler.getInstance();
			Object cacheObject = handler.getObj(cacheKey) ;
			System.out.println("cache get");
			if(cacheObject==null){
				// 执行切入点
				Object	ret = pjp.proceed();
				if(ret!=null){
					Date expiryTime = this.getExpiryTime(pjp);
					handler.addObj(cacheKey,ret,expiryTime);
				}
				return ret;
			}else{
				System.out.println("no cache");
				return cacheObject;
			}
		
		
	}
	public Date getExpiryTime(JoinPoint joinPoint){
		CacheableAnnotation cacheMethod = (CacheableAnnotation)AspectJUtil.getMetthodAnnotation(joinPoint, CacheableAnnotation.class);
		Calendar current = Calendar.getInstance();
		int after = 0;
		if(!StringUtils.isEmpty(cacheMethod.expiryTime())){
			after = Integer.parseInt(cacheMethod.expiryTime());
		}else {
			//默认过期时间为10分钟
			after=10;
		}
		current.add(Calendar.MINUTE,after);
		return current.getTime();
	 }
	private String getCacheKey(JoinPoint joinPoint){
		StringBuffer info = new StringBuffer();
		Signature sig = joinPoint.getStaticPart().getSignature();
		//类名
		String clsName = sig.getDeclaringType().getName();
		//方法名
		String methodName = sig.getName();
		String args = getArgsInfo(joinPoint);
        info.append(clsName)
     	.append(".")
     	.append(methodName)
     	.append("~")
     	.append(args);
     
		return info.toString();
	}
	public String getArgsInfo(JoinPoint joinPoint){
		StringBuffer info = new StringBuffer();
		Object[] args = joinPoint.getArgs();
		if(!ArrayUtils.isEmpty(args)){
			int length = args.length;
			for(int i=0; i<length; i++){
				if (args[i] instanceof CharSequence || args[i] instanceof Number) {
					info.append(args[i]);
				} else  {
					throw new RuntimeException("只支持参数为Number或CharSequence对象");
				}
				if(i < length -1){
					info.append('~');
				}
			}
		}
		return info.toString();
	}
}
