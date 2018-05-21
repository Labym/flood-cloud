package com.labym.flood.processor.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface DTOMapperContainer {
   DTOMapper [] value();
}
