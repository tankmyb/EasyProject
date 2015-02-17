package com.easy.kit.cache.ehcache;
import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target( { PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheKeyEx {

	String value() default "";//如果是对象bean，需要指定属性
}
