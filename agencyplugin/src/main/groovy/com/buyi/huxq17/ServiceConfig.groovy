package com.buyi.huxq17

import com.buyi.huxq17.serviceagency.ServiceAgency
import com.buyi.huxq17.serviceagency.annotation.ServiceAgent
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import javassist.ClassPool
import javassist.CtClass
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

import javax.lang.model.element.Modifier

public class ServiceConfig {
    private static final String SERVICE_CONFIG = "ServiceConfig"
    private List<String> contentList = new ArrayList<>()
    private ClassPool pool = ClassPool.default
    private static final String SERVICE_AGENT_NAME = "@${ServiceAgent.name}"
    private AgencyTransform transform

    ServiceConfig(Project project, AgencyTransform transform) {
        this.transform = transform
    }

    void addClassPath(String path) {
        pool.appendClassPath(path)
    }

    void update(String filePath) {
//        configFile.append("hello${System.getProperty("line.separator")}")
        println "updateServiceConfig path=${filePath}"
//        pool.appendClassPath(project.buildDir.toString()+"\\intermediates\\classes\\debug")
//        pool.appendClassPath(project.android.bootClasspath[0].toString())
//        pool.importPackage("android.support.annotation.AnyThread")
        CtClass clazz = pool.makeClass(new FileInputStream(filePath))
        if (!isService(clazz)) {
            return
        }
//        if (clazz.isFrozen()) {
//            clazz.defrost()
//        }
//        clazz.detach()
        String className = clazz.name
        if (!contentList.contains(className)) {
            contentList.add(className)
        }
    }

    boolean isService(CtClass clazz) {
        Object[] annotations = clazz.getAnnotations()
        println "annotation.length=" + annotations.length
        if (annotations.any { annotation -> (annotation.toString() == SERVICE_AGENT_NAME) }) {
            return true
        }
        return false
    }

    void commit() {
        String packageName = ServiceAgency.class.package.name
        if (contentList.size() == 0) {
            transform.classOutputDir.delete()
            return
        } else {
            transform.classOutputDir.mkdirs()
        }
        TypeSpec.Builder enmuBuild = TypeSpec.enumBuilder(SERVICE_CONFIG)
                .addModifiers(Modifier.PUBLIC)
        contentList.each { line ->
            enmuBuild.addEnumConstant(line.replace('.', '_'), TypeSpec.anonymousClassBuilder("\$S", line)
                    .build())
        }
        TypeSpec enmuType = enmuBuild.addField(String.class, "className", Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(MethodSpec.constructorBuilder()
                .addParameter(String.class, "className")
                .addStatement("this.\$N = \$N", "className", "className")
                .build())
                .build()

        def sourceDir = transform.sourceDir
        String fileName = "${packageName.replaceAll("\\.", "/")}/${SERVICE_CONFIG}.java"
        File sourceFile = new File(sourceDir, fileName)
        JavaFile javaFile = JavaFile.builder(packageName, enmuType).build()
        javaFile.writeTo(sourceDir)
        JavaCompilerUtil.CompilerJavaFile(sourceFile, transform.classOutputDir.absolutePath)
        FileUtils.deleteDirectory(new File(sourceDir, "com/buyi/"))
        contentList.clear()
    }
}
