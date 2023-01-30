package com.ali.rpc.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE
)
public @interface Rpc {

    String name();

    String value() default "";

    String url() default "";

    String fallback() default "";

    String fallbackFactory() default "";

    String type() default "";

    String interceptorClass() default "";

    boolean loadOnStartup() default true;

}
