package com.labym.flood.processor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface EnableCodeGenerator {
    boolean repository() default true;
    boolean service() default  true;
    boolean controller() default  true;
}
