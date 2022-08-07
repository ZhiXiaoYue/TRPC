package com.jill.rpc.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
// 运行时起作用
@Retention(RetentionPolicy.RUNTIME)
public @interface TRpcReference {
    String loadbalance() default "RandomLoadBalance";
}
