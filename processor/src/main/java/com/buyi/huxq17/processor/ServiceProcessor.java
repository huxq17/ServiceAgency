package com.buyi.huxq17.processor;

import com.buyi.huxq17.serviceagency.ServiceAgency;
import com.buyi.huxq17.serviceagency.annotation.ServiceAgent;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class ServiceProcessor extends AbstractProcessor {
    private Messager messager;
    private Elements elementUtils;
    private Filer filer;
    private List<String> configFileContentList = new ArrayList<>();
    private File configFile;
    private static final String SERVICE_CONFIG = "ServiceConfig";

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
//        FileObject fileObject = null;
//        try {
//            fileObject = filer.getResource(StandardLocation.SOURCE_OUTPUT, "", "ServiceAgency.cf");
//            File file = new File(fileObject.toUri());
//            System.out.println("root output location:" + file);
//            InputStreamReader openReader = (InputStreamReader) fileObject.openReader(false);
//            configFileContentList = getFileContent(openReader);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnv) {
        List<String> configFileContentList = new ArrayList<>();
        for (Element element : roundEnv.getElementsAnnotatedWith(ServiceAgent.class)) {
            if (element.getKind() != ElementKind.CLASS) {
                log("Builder annotation can only be applied to class", element);
                return false;
            }
            String packageName = elementUtils.getPackageOf(element).getQualifiedName().toString();
            String className = element.getSimpleName().toString();
            String fullName = packageName+"." + className;
            log("class $"+fullName+" had annotate with "+ServiceAgent.class.getCanonicalName(), element);
            configFileContentList.add(fullName);
        }
        if (configFileContentList.size() > 0) {
            TypeSpec.Builder enmuBuild = TypeSpec.enumBuilder(SERVICE_CONFIG)
                    .addModifiers(Modifier.PUBLIC);
            for (String fullName: configFileContentList ) {
                enmuBuild.addEnumConstant(fullName.replace('.', '_'), TypeSpec.anonymousClassBuilder("$S", fullName)
                        .build());
            }
            TypeSpec enmuType = enmuBuild.addField(String.class, "className", Modifier.PUBLIC, Modifier.FINAL)
                    .addMethod(MethodSpec.constructorBuilder()
                            .addParameter(String.class, "className")
                            .addStatement("this.$N = $N", "className", "className")
                            .build())
                    .build();
            JavaFile javaFile = JavaFile.builder(ServiceAgency.class.getPackage().getName(), enmuType).build();
            try {
                javaFile.writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private void onError(String message, Element element) {
        messager.printMessage(Diagnostic.Kind.ERROR, message, element);
    }

    private void log(String message, Element element) {
        messager.printMessage(Diagnostic.Kind.OTHER, message, element);
    }

    private ArrayList<String> getFileContent(InputStreamReader openReader) {
        ArrayList<String> arrayList = new ArrayList<>();
        BufferedReader bReader = null;
        try {
            bReader = new BufferedReader(openReader);
            String str;
            while ((str = bReader.readLine()) != null) {
                arrayList.add(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bReader != null) {
                    bReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return arrayList;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(ServiceAgent.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
