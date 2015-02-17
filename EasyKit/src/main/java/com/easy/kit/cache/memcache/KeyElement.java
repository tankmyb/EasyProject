package com.easy.kit.cache.memcache;
import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target( { PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface KeyElement {

}
