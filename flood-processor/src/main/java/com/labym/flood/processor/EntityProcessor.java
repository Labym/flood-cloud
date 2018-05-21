package com.labym.flood.processor;

import com.google.auto.service.AutoService;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.labym.flood.processor.annotation.DTO;
import com.labym.flood.processor.annotation.DTOMapper;
import com.labym.flood.processor.annotation.EnableCodeGenerator;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.sun.source.tree.Tree;
import com.sun.source.util.TreePath;
import com.sun.source.util.Trees;
import com.sun.tools.javac.code.Attribute;
import com.sun.tools.javac.code.SymbolMetadata;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Options;
import com.sun.tools.javac.util.StringUtils;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
import java.io.File;
import java.lang.reflect.Type;
import java.net.URI;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

@SupportedAnnotationTypes({"javax.persistence.Entity"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class EntityProcessor extends AbstractProcessor {

    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;
    private Messager messager;
    TreeMaker treeMaker;
    JCTree.JCAnnotation c;
    Trees trees;
    JavacProcessingEnvironment processingEnvironment;
    private Options options;
    private String sourcePath="C:\\github\\flood-cloud\\flood-iam\\src\\main\\java";

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        processingEnvironment = ((JavacProcessingEnvironment) processingEnv);

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fm = compiler.getStandardFileManager(null, null, null);

//        Iterable<? extends File> locations = fm.getLocation(StandardLocation.SOURCE_PATH);
//        locations.forEach(f->{
//            System.out.println(f.getAbsolutePath());
//        });
//        options = Options.instance(((JavacProcessingEnvironment) processingEnv).getContext());
//        String paths = options.get("-sourcepath");
//        java.util.List<String> strings = Splitter.on(";").splitToList(paths);
 //       strings.stream().findFirst().ifPresent((path)->sourcePath=path);
        //sourcePath = sourcePath.substring(0, sourcePath.indexOf("target"))+"src\\main\\java";
        EntityGenerator.setSource(sourcePath);
        EntityGenerator.setSourcePath(Paths.get(sourcePath));
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        TypeElement typeElement = elementUtils.getTypeElement(IdClass.class.getName());
        EntityGenerator.setIdClassType(typeElement.asType());
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
        trees = Trees.instance(processingEnv);
        treeMaker = TreeMaker.instance(processingEnvironment.getContext());
    }


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("start EntityProcessor ===============================");
        Set<? extends Element> entityClasses = roundEnv.getElementsAnnotatedWith(Entity.class);
        if (null == entityClasses || entityClasses.isEmpty()) {
            return true;
        }
        entityClasses.stream().forEach(this::processElement);
        System.out.println("End EntityProcessor ===============================");
        return true;
    }

    private void processElement(Element element) {
        ElementKind kind = element.getKind();
        if (kind != ElementKind.CLASS) {
            return;
        }
        EnableCodeGenerator codeGenerator = element.getAnnotation(EnableCodeGenerator.class);
        if (null == codeGenerator) {
            return;
        }

        DTO dto = element.getAnnotation(DTO.class);
        DTOMapper[] dtoMappers = element.getAnnotationsByType(DTOMapper.class);
        TypeElement classElement = (TypeElement) element;

        TreePath path = trees.getPath(classElement);
        JCTree.JCCompilationUnit compilationUnit = ((JCTree.JCCompilationUnit) path.getCompilationUnit());

        compilationUnit.getTypeDecls().forEach((decl) -> {

            if (decl.getKind() == Tree.Kind.CLASS) {
                JCTree.JCClassDecl classDecl = (JCTree.JCClassDecl) decl;
                List<Attribute.Compound> declarationAttributes = classDecl.sym.getMetadata().getDeclarationAttributes();
                declarationAttributes.forEach(System.out::println);
                List<JCTree> members = classDecl.getMembers();
                java.util.List<Field> fields = Lists.newArrayList();
                members.forEach((member)->{
                    if(member.getKind()!=Tree.Kind.VARIABLE){
                        return;
                    }
                    JCTree.JCVariableDecl variable = (JCTree.JCVariableDecl) member;
                    if (variable.mods.getFlags().contains(Modifier.STATIC)) {
                        return;
                    }
                    DTOMapper dtoMapper = variable.sym.getAnnotation(DTOMapper.class);

                    Field field = new Field();
                    field.setName(variable.getName().toString());
//                    String type=variable.getType().type.tsym.type.toString();
                    if (variable.getType().type.isParameterized()) {
                        field.setType(ParameterizedTypeName.get(variable.getType().type));
                    }else {
                        field.setType(ClassName.get(variable.getType().type));
                    }

                    field.setDtoName(field.getName());

                    if(null!=dtoMappers){
                        for (DTOMapper mapper : dtoMappers) {
                            if (field.getName().equals(mapper.from())&&null!=Strings.emptyToNull(mapper.to())) {
                                field.setDtoName(mapper.to());
                                break;
                            }
                        }
                    }


                    if(null!=dtoMapper&&null!=Strings.emptyToNull(dtoMapper.to())){
                        field.setDtoName(dtoMapper.to());
                    }

                    fields.add(field);
                });

                String entityPackage = compilationUnit.getPackageName().toString();

                GeneratorInfo generatorInfo = GeneratorInfo.builder()
                        .dto(dto)
//                        .element(classElement)
//                        .node(compilationUnit)
//                        .classDecl(classDecl)
//                        .member(members)
                        .repositoryEnabled(codeGenerator.repository())
                        .serviceEnabled(codeGenerator.service())
                        .controllerEnabled(codeGenerator.controller())
                        .idClass(getEntityIdClass(classDecl))
                        .sourcePackage(entityPackage.substring(0,entityPackage.lastIndexOf(".model")))
                        .entityPackage(entityPackage)
                        .entityClassName(classElement.getSimpleName().toString().replace("PO",""))
                        .fields(fields)
                        .build();

                EntityGenerator.generate(generatorInfo);
            }

        });
    }

    private TypeName getEntityIdClass(JCTree.JCClassDecl classDecl) {
        return findByIdClass(classDecl).orElse(findById(classDecl));
    }

    private Optional<TypeName> findByIdClass(JCTree.JCClassDecl classDecl) {
        return classDecl.sym.getMetadata().getDeclarationAttributes().stream().filter((attr) -> {
            if (null != attr.type && IdClass.class.getName().equals(attr.type.tsym.getQualifiedName().toString())) {
                return true;
            }
            return false;
        }).findAny().map((attr) ->
                attr.getElementValues().values().stream().findAny().map((attribute ->
                        ClassName.get((com.sun.tools.javac.code.Type.ClassType) attribute.getValue())
                )).orElseThrow(() -> new IllegalArgumentException(""))

        );
    }

    private TypeName findById(JCTree.JCClassDecl classDecl) {
        for (JCTree member : classDecl.getMembers()) {
            if (member.getKind() != Tree.Kind.VARIABLE) {
                continue;
            }
            JCTree.JCVariableDecl variable = (JCTree.JCVariableDecl) member;
            SymbolMetadata metadata = variable.sym.getMetadata();
            if(null==metadata){
                continue;
            }
            List<Attribute.Compound> attributes = metadata.getDeclarationAttributes();
            Optional<TypeName> typeName = attributes.stream().findAny().filter((attr) -> {
                if (null != attr.type && Id.class.getName().equals(attr.type.tsym.getQualifiedName().toString())) {
                    return true;
                }
                return false;
            }).map((compound -> {
                System.out.println(variable.getType());
                return ClassName.get(variable.getType().type);
            }));
            if (typeName.isPresent()) {
                return typeName.get();
            }
        }
        throw new IllegalArgumentException("can't find primary key for class:");
    }

    private String bestGuess(String type){
        if ("boolean".equals(type)) {
            return boolean.class.getTypeName();
        }

        if("int".equals(type)){
            return int.class.getTypeName();
        }

        if("long".equals(type)){
            return long.class.getTypeName();
        }

        if("short".equals(type)){
            return short.class.getTypeName();
        }
        if("byte".equals(type)){
            return byte.class.getTypeName();
        }

        return type;
    }
}
