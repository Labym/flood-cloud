package com.labym.flood.processor;

import com.labym.flood.processor.annotation.DTO;
import com.labym.flood.processor.annotation.DTOMapper;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.List;
import lombok.Builder;
import lombok.Data;

import javax.lang.model.element.TypeElement;

@Data
@Builder
public class GeneratorInfo {

    private TypeElement element;
    private JCTree.JCCompilationUnit node;
    private JCTree.JCClassDecl classDecl;
    private List<JCTree> member;
    private String fileName;
    private DTO dto;

    private TypeName idClass;
    private String sourcePackage;
    private String entityPackage;
    private String entityClassName;
    private java.util.List<Field> fields;
    private java.util.List<DTOMapper> dtoMappers;
    private boolean repositoryEnabled;
    private boolean serviceEnabled;
    private boolean controllerEnabled;

    public TypeName entityType(){
        return ClassName.get(entityPackage,entityClassName);
    }

}
