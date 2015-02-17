package com.easy.persistance.aop;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.easy.persistance.common.SQLAssistant;
import com.easy.persistance.common.TimeMeter;
import com.easy.persistance.log.SqlLogger;
import com.easy.persistance.util.PrintSqlUtil;

@Component
@Aspect
public class PrintSqlListAspect {
	@Pointcut("execution(@com.easy.persistance.aop.PrintSqlListAnnotation * *.*(..))")
	public void inShowSqlList() {
	}

	@SuppressWarnings("unchecked")
	@Around("inShowSqlList()")
	public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
		TimeMeter timeMeter = null;
		if (SQLAssistant.isShowSql) {
			timeMeter = new TimeMeter();
		}
		// 执行切入点
		Object retVal = pjp.proceed();
		if (timeMeter != null) {
			Object[] args = pjp.getArgs();
			String sql = (String) args[0];
				Object obj = args[1];
				SqlLogger.sql(timeMeter,PrintSqlUtil.packageSqlList(sql, (List)obj));
			}
		return retVal;
	}

}
