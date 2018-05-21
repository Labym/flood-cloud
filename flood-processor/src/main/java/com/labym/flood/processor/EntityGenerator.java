package com.labym.flood.processor;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.labym.flood.processor.annotation.DTO;
import com.squareup.javapoet.*;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;

import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
@Slf4j
public class EntityGenerator {

    private static final Map<String, Object> TEMP = new HashMap<>();
    @Setter
    private static TypeMirror idClassType;
    @Setter
    private static Path sourcePath;

    @Setter
    private static String source;

    public static void generate(GeneratorInfo info){
        if (info.isRepositoryEnabled()) {
            generateRepository(info);
        }

        if (info.isServiceEnabled()) {
            generateService(info);
        }

        if (info.isControllerEnabled()) {
            generateEndpoint(info);
        }


        generateDTO(info);
        generateMapper(info);
    }


    public static void generateService(GeneratorInfo info) {
        generateServiceInterface(info);
        generateServiceImpl(info);
    }

    private static void generateServiceInterface(GeneratorInfo info) {
        String serviceInterfaceClassName=format(Constants.SERVICE_INTERFACE_FORMAT,info.getEntityClassName());
        String serviceInterfacePackage=format(Constants.SERVICE_INTERFACE_PACKAGE_FORMAT, info.getSourcePackage());
        Path path = Paths.get(source, serviceInterfacePackage.replace(".", "\\"), serviceInterfaceClassName + ".java");
        if (Files.exists(path)) {
            log.info("class {} already generated service interface ",info.getEntityClassName());
            return ;
        }

        TypeSpec.Builder serviceInterface =TypeSpec.interfaceBuilder(serviceInterfaceClassName).addModifiers(Modifier.PUBLIC);

        JavaFile serviceInterfaceJavaFile = JavaFile
                .builder(serviceInterfacePackage,   serviceInterface.build())
                .build();

        try {

            serviceInterfaceJavaFile.writeTo(
                    sourcePath
            );

            log.info("generate service interface code for entity class {} success",info.getEntityClassName());


        } catch (IOException e) {
            log.error("generate service interface code for entity class {} failed,",info.getEntityClassName(),e);
        }
    }
    private static void generateMapper(GeneratorInfo info) {

        if(null==info.getDto()){
            return;
        }
        String mapperClassName=format(Constants.MAPPER_FORMAT,info.getEntityClassName());
        String mapperPackage=format(Constants.MAPPER_PACKAGE_FORMAT, info.getSourcePackage());
        String dtoClassName=format(Constants.DTO_FORMAT,info.getEntityClassName());
        String dtoPackage=format(Constants.DTO_PACKAGE_FORMAT, info.getSourcePackage());
        Path path = Paths.get(source, mapperPackage.replace(".", "\\"), mapperClassName + ".java");
        if (Files.exists(path)) {
            log.info("class {} already generated service implement ",info.getEntityClassName());
            return ;
        }


        ClassName dto = ClassName.get(dtoPackage, dtoClassName);

        String entityLow = CaseFormat.UPPER_CAMEL.converterTo(CaseFormat.LOWER_CAMEL).convert(info.getEntityClassName());
        String dtoLow=entityLow+"DTO";
        String dtoLowBuilder=dtoLow+"Builder";


        MethodSpec.Builder entityToDTOMethod = buildEntityToDTOMethod(info, dto, entityLow, dtoLow);
        MethodSpec.Builder dtoToEntityMethod = buildDTOToEntityMethod(info, dto, entityLow, dtoLow);
        ParameterizedTypeName supperClass = ParameterizedTypeName.get(Constants.ENTITY_MAPPER_CLASS, dto,info.entityType());
        AnnotationSpec mapperAnn = AnnotationSpec.builder(Constants.MAPPER_CLASS).addMember("componentModel","$S", "spring").build();

        TypeSpec.Builder service =TypeSpec
                .interfaceBuilder(mapperClassName)
                .addMethod(dtoToEntityMethod.build())
                .addMethod(entityToDTOMethod.build())
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(mapperAnn)
                .addSuperinterface(supperClass)
                //.addAnnotation(Constants.SPRING_SERVICE_CLASS)
               ;

        JavaFile serviceJavaFile = JavaFile
                .builder(mapperPackage,   service.build())
                .build();

        try {

            serviceJavaFile.writeTo(
                    sourcePath
            );

            log.info("generate service implement code for entity class {} success",info.getEntityClassName());


        } catch (IOException e) {
            log.error("generate service implement code for entity class {} failed,",info.getEntityClassName(),e);
        }
    }

    private static MethodSpec.Builder buildEntityToDTOMethod(GeneratorInfo info, ClassName dto, String entityLow, String dtoLow) {
//        CodeBlock.Builder entityTODTOCode = CodeBlock.builder().beginControlFlow("if (null==$L)", entityLow)
//                .add("return null;\n")
//                .endControlFlow()
//                ;
//        DTO dtoAnnotation = info.getDto();
//        int size=(null!=dtoAnnotation.of()&&dtoAnnotation.of().length>=1)?dtoAnnotation.of().length:(info.getFields().size()-dtoAnnotation.exclude().length);
//        if(size>4){
//            entityTODTOCode.add("$T $L = $T.builder()\n", dto, dtoLow, dto);
//        }else{
//            entityTODTOCode.add("$T $L = new $T();\n", dto, dtoLow, dto);
//
//        }
//
//        info.getFields().forEach((field -> {
//            if(dtoAnnotation.of().length>0){
//                for (String s : dtoAnnotation.of()) {
//                    if(s.equals(field.getName())){
//                        String filedNameUpper = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.UPPER_CAMEL).convert(field.getName());
//                        String dtoNameUpper = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.UPPER_CAMEL).convert(field.getDtoName());
//                        if(size>4){
//                            entityTODTOCode.add("                .$L($N.get$L())\n", field.getDtoName(), entityLow, filedNameUpper);
//                        }else{
//                            entityTODTOCode.add("$L.set$L($N.get$L());\n", dtoLow, dtoNameUpper, entityLow,filedNameUpper);
//
//                        }
//                    }
//                }
//
//                return;
//            }
//
//            for (String s : dtoAnnotation.exclude()) {
//                if (s.equals(field.getName())) {
//                    return;
//                }
//            }
//            String filedNameUpper = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.UPPER_CAMEL).convert(field.getName());
//            String dtoNameUpper = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.UPPER_CAMEL).convert(field.getDtoName());
//            if(size>4){
//                entityTODTOCode.add("                .$L($N.get$L())\n", field.getDtoName(), entityLow, filedNameUpper);
//            }else{
//                entityTODTOCode.add("$L.set$L($N.get$L());\n", dtoLow, dtoNameUpper, entityLow,filedNameUpper);
//
//            }
//        }));
//
//        if(size>4){
//            entityTODTOCode.add("                .build();\n");
//        }

            List<AnnotationSpec> mappings= Lists.newArrayList();
            DTO dtoAnnotation = info.getDto();
//            for (String s : dtoAnnotation.exclude()) {
//                AnnotationSpec.Builder mapping = AnnotationSpec.builder(Constants.MAPPING_CLASS).addMember("target", "$S", s)
//                        .addMember("ignore", "$L", true);
//                mappings.add(mapping.build());
//
//            }

        return MethodSpec.methodBuilder("toDto")
                .addParameter(info.entityType(), entityLow)
                .addAnnotations(mappings)
                .returns(dto).addModifiers(Modifier.PUBLIC,Modifier.ABSTRACT)
                ;
    }

    private static MethodSpec.Builder buildDTOToEntityMethod(GeneratorInfo info, ClassName dto, String entityLow, String dtoLow) {
//        CodeBlock.Builder entityTODTOCode = CodeBlock.builder().beginControlFlow("if (null==$L)", dtoLow)
//                .add("return null;\n")
//                .endControlFlow()
//                ;
//        DTO dtoAnnotation = info.getDto();
//        int size=(null!=dtoAnnotation.of()&&dtoAnnotation.of().length>=1)?dtoAnnotation.of().length:(info.getFields().size()-dtoAnnotation.exclude().length);
//
//        entityTODTOCode.add("$T $L = new $T();\n", info.entityType(), entityLow, info.entityType());
//
//        info.getFields().forEach((field -> {
//            if(dtoAnnotation.of().length>0){
//                for (String s : dtoAnnotation.of()) {
//                    if(s.equals(field.getName())){
//                        String filedNameUpper = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.UPPER_CAMEL).convert(field.getName());
//                        String dtoNameUpper = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.UPPER_CAMEL).convert(field.getDtoName());
//                        entityTODTOCode.add("$N.set$L($N.get$L());\n",entityLow,filedNameUpper,dtoLow,dtoNameUpper);
//                    }
//                }
//
//                return;
//            }
//
//            for (String s : dtoAnnotation.exclude()) {
//                if (s.equals(field.getName())) {
//                    return;
//                }
//            }
//            String filedNameUpper = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.UPPER_CAMEL).convert(field.getName());
//            String dtoNameUpper = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.UPPER_CAMEL).convert(field.getDtoName());
//            entityTODTOCode.add("$N.set$L($N.get$L());\n",entityLow,filedNameUpper,dtoLow,dtoNameUpper);
//        }));
//
//
//        entityTODTOCode.add("return $N;\n",entityLow);


        return MethodSpec.methodBuilder( "toEntity")
                .addParameter(dto, dtoLow)
                .returns(info.entityType()).addModifiers(Modifier.PUBLIC,Modifier.ABSTRACT)
               ;
    }

    private static void generateServiceImpl(GeneratorInfo info) {
        String serviceClassName=format(Constants.SERVICE_IMPL_FORMAT,info.getEntityClassName());
        String servicePackage=format(Constants.SERVICE_IMPL_PACKAGE_FORMAT, info.getSourcePackage());
        String serviceInterfaceClassName=format(Constants.SERVICE_INTERFACE_FORMAT,info.getEntityClassName());
        String serviceInterfacePackage=format(Constants.SERVICE_INTERFACE_PACKAGE_FORMAT, info.getSourcePackage());
        Path path = Paths.get(source, servicePackage.replace(".", "\\"), serviceClassName + ".java");
        if (Files.exists(path)) {
            log.info("class {} already generated service implement ",info.getEntityClassName());
            return ;
        }

        String repositoryClassName=format(Constants.RESPOSITORY_FORMAT,info.getEntityClassName());
        String repositoryPackage=format(Constants.REPOSITORY_PACKAGE_FORMAT, info.getSourcePackage());
        ClassName serviceInterface = ClassName.get(serviceInterfacePackage, serviceInterfaceClassName);
        ClassName repository = ClassName.get(repositoryPackage, repositoryClassName);

        String repositoryFieldName = CaseFormat.UPPER_CAMEL.converterTo(CaseFormat.LOWER_CAMEL).convert(repositoryClassName);
        FieldSpec repositoryField = FieldSpec.builder(repository, repositoryFieldName, Modifier.PRIVATE,Modifier.FINAL).build();


        MethodSpec constructor = MethodSpec.constructorBuilder()
                .addParameter(repository, repositoryFieldName)
                .addCode("  this.$L = $L;\n", repositoryFieldName, repositoryFieldName)
                .addModifiers(Modifier.PUBLIC).build();


        TypeSpec.Builder service =TypeSpec
                .classBuilder(serviceClassName)
                .addField(repositoryField)
                .addMethod(constructor)
                .addAnnotation(Constants.SPRING_SERVICE_CLASS)
                .addSuperinterface(serviceInterface).addModifiers(Modifier.PUBLIC);

        JavaFile serviceJavaFile = JavaFile
                .builder(servicePackage,   service.build())
                .build();

        try {

            serviceJavaFile.writeTo(
                    sourcePath
            );

            log.info("generate service implement code for entity class {} success",info.getEntityClassName());


        } catch (IOException e) {
            log.error("generate service implement code for entity class {} failed,",info.getEntityClassName(),e);
        }
    }

    private static void generateEndpoint(GeneratorInfo info) {
        String endpointClassName=format(Constants.CONTROLLER_FORMAT,info.getEntityClassName());
        String endpointPackage=format(Constants.CONTROLLER_PACKAGE_FORMAT, info.getSourcePackage());
        String serviceInterfaceClassName=format(Constants.SERVICE_INTERFACE_FORMAT,info.getEntityClassName());
        String serviceInterfacePackage=format(Constants.SERVICE_INTERFACE_PACKAGE_FORMAT, info.getSourcePackage());
        Path path = Paths.get(source, endpointPackage.replace(".", "\\"), endpointClassName + ".java");
        if (Files.exists(path)) {
            log.info("class {} already generated service implement ",info.getEntityClassName());
            return ;
        }

        ClassName serviceInterface = ClassName.get(serviceInterfacePackage, serviceInterfaceClassName);


        String serviceFieldName = CaseFormat.UPPER_CAMEL.converterTo(CaseFormat.LOWER_CAMEL).convert(serviceInterfaceClassName);
        FieldSpec repositoryField = FieldSpec.builder(serviceInterface, serviceFieldName, Modifier.PRIVATE,Modifier.FINAL).build();


        MethodSpec constructor = MethodSpec.constructorBuilder()
                .addParameter(serviceInterface, serviceFieldName)
                .addCode("  this.$L = $L;\n", serviceFieldName, serviceFieldName)
                .addModifiers(Modifier.PUBLIC).build();


        TypeSpec.Builder service =TypeSpec
                .classBuilder(endpointClassName)
                .addField(repositoryField)
                .addMethod(constructor)
                .addAnnotation(Constants.SPRING_REST_CONTROLLER_CLASS)
              .addModifiers(Modifier.PUBLIC);

        JavaFile serviceJavaFile = JavaFile
                .builder(endpointPackage,   service.build())
                .build();

        try {

            serviceJavaFile.writeTo(
                    sourcePath
            );

            log.info("generate service implement code for entity class {} success",info.getEntityClassName());


        } catch (IOException e) {
            log.error("generate service implement code for entity class {} failed,",info.getEntityClassName(),e);
        }
    }

    public static void generateRepository(GeneratorInfo info) {

        String repositoryClassName=format(Constants.RESPOSITORY_FORMAT,info.getEntityClassName());
        String repositoryPackage=format(Constants.REPOSITORY_PACKAGE_FORMAT, info.getSourcePackage());
        Path path = Paths.get(source, repositoryPackage.replace(".", "\\"), repositoryClassName + ".java");
        if (Files.exists(path)) {
            log.info("class {} already generated repository ",info.getEntityClassName());
            return ;
        }

        TypeSpec.Builder repository = TypeSpec.interfaceBuilder(repositoryClassName).addModifiers(Modifier.PUBLIC);

        ParameterizedTypeName supperClass = ParameterizedTypeName.get(Constants.SPRING_JPA_REPOSITORY_CLASS, ClassName.bestGuess(info.getEntityClassName()), info.getIdClass());
        repository.addSuperinterface(supperClass);

        JavaFile repositoryJavaFile = JavaFile
                .builder(repositoryPackage,   repository.build())
                .build();

        try {

            repositoryJavaFile.writeTo(
                    sourcePath
            );

            log.info("generate repository code for entity class {} success",info.getEntityClassName());


        } catch (IOException e) {
            log.error("generate repository code for entity class {} failed,",info.getEntityClassName(),e);
        }

    }


    public static void generateDTO(GeneratorInfo info) {

        if(null==info.getDto()){
            return;
        }


        String dtoClassName=format(Constants.DTO_FORMAT,info.getEntityClassName());
        String dtoPackage=format(Constants.DTO_PACKAGE_FORMAT, info.getSourcePackage());
        Path path = Paths.get(source, dtoPackage.replace(".", "\\"), dtoClassName + ".java");
        if (Files.exists(path)) {
            log.info("class {} already generated dto ",info.getEntityClassName());
            return ;
        }

        DTO dtoAnnotation = info.getDto();
        int size=(null!=dtoAnnotation.of()&&dtoAnnotation.of().length>=1)?dtoAnnotation.of().length:(info.getFields().size()-dtoAnnotation.exclude().length);

        TypeSpec.Builder dto = TypeSpec
                .classBuilder(dtoClassName)
                .addAnnotation(Constants.LOMBOK_DATA)
                .addModifiers(Modifier.PUBLIC);

        if(size>4){
            dto.addAnnotation(Constants.LOMBOK_BUILDER);
            dto.addAnnotation(Constants.LOMBOK_NO_ARGS_CONSTRUCTOR);
            dto.addAnnotation(Constants.LOMBOK_ALL_ARGS_CONSTRUCTOR);
        }

        info.getFields().forEach((field -> {
            if(dtoAnnotation.of().length>0){
                for (String s : dtoAnnotation.of()) {
                    if(s.equals(field.getName())){
                        //ClassName type = ClassName.bestGuess(field.getType());
                        dto.addField(FieldSpec.builder(field.getType(),field.getDtoName(),Modifier.PRIVATE).build());
                    }
                }

                return;
            }

            for (String s : dtoAnnotation.exclude()) {
                if (s.equals(field.getName())) {
                    return;
                }
            }
            //ClassName type = ClassName.bestGuess(field.getType());
            dto.addField(FieldSpec.builder(field.getType(),field.getDtoName(),Modifier.PRIVATE).build());

        }));

        JavaFile dtoJavaFile = JavaFile
                .builder(dtoPackage,   dto.build())
                .build();

        try {

            dtoJavaFile.writeTo(
                    sourcePath
            );

            log.info("generate dto code for entity class {} success",info.getEntityClassName());

        } catch (IOException e) {
            log.error("generate dto code for entity class {} failed,",info.getEntityClassName(),e);
        }

    }


}
