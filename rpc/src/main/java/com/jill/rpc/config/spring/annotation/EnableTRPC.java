package com.jill.rpc.config.spring.annotation;

import com.jill.rpc.config.spring.TRPCConfiguration;
import com.jill.rpc.config.spring.TRPCPostProcessor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({TRPCPostProcessor.class, TRPCConfiguration.class })
public @interface EnableTRPC {
}
