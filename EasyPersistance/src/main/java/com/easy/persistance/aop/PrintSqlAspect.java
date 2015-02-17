package com.easy.persistance.aop;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
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
public class PrintSqlAspect {
	@Pointcut("execution(@com.easy.persistance.aop.PrintSqlAnnotation * *.*(..))")
	public void inShowSql() {
	}

	@SuppressWarnings("unchecked")
	@Around("inShowSql()")
	public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
		TimeMeter timeMeter = null;
		if (SQLAssistant.isShowSql) {
			timeMeter = new TimeMeter();
		}
		// 执行切入点
		Object retVal = pjp.proceed();
		if (timeMeter != null) {
			Object[] args = pjp.getArgs();
			int len = args.length;
			String sql = (String) pjp.getArgs()[0];
			if (len == 1) {
				SqlLogger.sql(timeMeter, sql);
			} else {
				Object obj = pjp.getArgs()[1];
				if (obj instanceof List) {
					SqlLogger.sql(timeMeter,PrintSqlUtil.packageSql(sql, (List)obj));
				}
			}
		}
		return retVal;
	}

}
