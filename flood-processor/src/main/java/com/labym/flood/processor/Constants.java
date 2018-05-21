package com.labym.flood.processor;

import com.squareup.javapoet.ClassName;

public class Constants {

    public static final String RESPOSITORY_FORMAT="%sRepository";

    public static final String SERVICE_INTERFACE_FORMAT="%sService";
    public static final String SERVICE_IMPL_FORMAT="%sServiceImpl";
    public static final String REPOSITORY_PACKAGE_FORMAT ="%s.repository";
    public static final String DTO_PACKAGE_FORMAT ="%s.model.dto";
    public static final String DTO_FORMAT="%sDTO";

    public static final String MAPPER_PACKAGE_FORMAT ="%s.service.mapper";
    public static final String MAPPER_FORMAT="%sMapper";

    public static final String CONTROLLER_PACKAGE_FORMAT ="%s.web.rest";
    public static final String CONTROLLER_FORMAT="%sEndpoint";

    public static final String SERVICE_INTERFACE_PACKAGE_FORMAT="%s.service";
    public static final String SERVICE_IMPL_PACKAGE_FORMAT="%s.service.impl";
    public static final ClassName LOMBOK_DATA=ClassName.bestGuess("lombok.Data");
    public static final ClassName LOMBOK_BUILDER=ClassName.bestGuess("lombok.Builder");
    public static final ClassName LOMBOK_NO_ARGS_CONSTRUCTOR=ClassName.bestGuess("lombok.NoArgsConstructor");
    public static final ClassName LOMBOK_ALL_ARGS_CONSTRUCTOR=ClassName.bestGuess("lombok.AllArgsConstructor");

    public static final ClassName SPRING_SERVICE_CLASS=ClassName.get("org.springframework.stereotype","Service");
    public static final ClassName SPRING_JPA_REPOSITORY_CLASS=ClassName.bestGuess("org.springframework.data.jpa.repository.JpaRepository");


    public static final ClassName SPRING_REST_CONTROLLER_CLASS=ClassName.get(" org.springframework.web.bind.annotation","RestController");

    public static final ClassName SPRING_REQUESTMAPPING_CLASS=ClassName.get(" org.springframework.web.bind.annotation","RequestMapping");


    public static final ClassName MAPPER_CLASS=ClassName.get("org.mapstruct","Mapper");

    public static final ClassName MAPPING_CLASS=ClassName.get("org.mapstruct","Mapping");

    public static final ClassName ENTITY_MAPPER_CLASS=ClassName.get("com.labym.flood.common.service","EntityMapper");


}
