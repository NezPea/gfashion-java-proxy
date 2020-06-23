package com.gfashion.api.log.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DDBLog {
    String operationEvent() default "";

    String operationType() default "";
}
