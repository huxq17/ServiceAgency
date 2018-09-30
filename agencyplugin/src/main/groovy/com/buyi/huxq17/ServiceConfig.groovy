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
    private static final String SERVICE_AGENT_NAME = ServiceAgent.name
    private AgencyTransform transform
    private File configFile

    ServiceConfig(Project project, AgencyTransform transform) {
        this.transform = transform
        configFile = new File(project.buildDir, "\\intermediates\\transforms\\AgencyTransform\\ServiceAgency.cf")
        if (!configFile.exists()) {
            configFile.createNewFile()
        }
        configFile.eachLine { className ->
            if (!contentList.contains(className)) {
                contentList.add(className)
            }
        }
    }

    void addClassPath(String path) {
        pool.appendClassPath(path)
    }

    void update(File file) {
        if (file.exists()) {
            update(file.getName(), new FileInputStream(file))
        } else {
            String fileName = file.getName()
            if (!fileName.endsWith(".class")) {
                return
            }
            String className = fileName.split(".class")[0]
            Iterator<String> iterator = contentList.iterator()
            while (iterator.hasNext()) {
                String next = iterator.next()
                if ((next.endsWith(className))) {
                    iterator.remove()
                }
            }
        }
    }

    void update(String fileName, InputStream classfile) {
//        configFile.append("hello${System.getProperty("line.separator")}")
//        println "updateServiceConfig path=${fileName}"
//        pool.appendClassPath(project.buildDir.toString()+"\\intermediates\\classes\\debug")
        CtClass clazz = pool.makeClass(classfile)
        if (!isService(clazz)) {
            contentList.remove(clazz.name)
            return
        }
//        if (clazz.isFrozen()) {
//            clazz.defrost()
//        }
        clazz.detach()
        String className = clazz.name
        if (!contentList.contains(className)) {
            contentList.add(className)
        }
    }

    boolean isService(CtClass clazz) {
        if (clazz.hasAnnotation(ServiceAgent.class)) {
            println "class ${clazz.name} had annotate with $SERVICE_AGENT_NAME"
            return true
        }
        return false
    }

    void commit() {
        if (contentList.size() == 0) {
            if (configFile.exists())
                configFile.delete()
            return
        }
        String packageName = ServiceAgency.class.package.name

        TypeSpec.Builder enmuBuild = TypeSpec.enumBuilder(SERVICE_CONFIG)
                .addModifiers(Modifier.PUBLIC)
        PrintWriter printWriter = configFile.newPrintWriter()
        contentList.each { line ->
            printWriter.println(line)
            enmuBuild.addEnumConstant(line.replace('.', '_'), TypeSpec.anonymousClassBuilder("\$S", line)
                    .build())
        }
        printWriter.flush()
        printWriter.close()
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
