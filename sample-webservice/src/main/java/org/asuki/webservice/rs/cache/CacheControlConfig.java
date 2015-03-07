package org.asuki.webservice.rs.cache;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({ FIELD, PARAMETER })
public @interface CacheControlConfig {

    boolean isPrivate() default true;

    boolean noCache() default false;

    boolean noStore() default false;

    boolean noTransform() default true;

    boolean mustRevalidate() default true;

    boolean proxyRevalidate() default false;

    int maxAge() default 0;

    int sMaxAge() default 0;

}
