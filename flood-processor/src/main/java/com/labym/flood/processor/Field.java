package com.labym.flood.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import lombok.Data;

@Data
public class Field {
    private String name;
    private TypeName type;
    private String dtoName;
}
