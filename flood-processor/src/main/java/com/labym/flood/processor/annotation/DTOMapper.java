package com.labym.flood.processor.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.SOURCE)
@Repeatable(DTOMapperContainer.class)
public @interface DTOMapper {
   String from() default "";
   String to();
}
