package com.buyi.huxq17

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

import java.util.jar.JarEntry
import java.util.jar.JarFile

class AgencyTransform extends Transform {
    private Project mProject
    private ServiceConfig serviceConfig
    private File sourceDir
    File classOutputDir

    File getSourceDir() {
        return sourceDir
    }

    AgencyTransform(Project project) {
        mProject = project
    }

    @Override
    String getName() {
        return "AgencyTransform"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        Context context = transformInvocation.getContext()
        initServiceConfig(context.variantName)
        Collection<TransformInput> inputs = transformInvocation.getInputs()
        Collection<TransformInput> referencedInputs = transformInvocation.getReferencedInputs()
        TransformOutputProvider outputProvider = transformInvocation.getOutputProvider()
        classOutputDir = outputProvider.getContentLocation("serviceagency", getOutputTypes(), getScopes(), Format.DIRECTORY)
        if (classOutputDir.exists()) {
            FileUtils.deleteDirectory(classOutputDir)
        }
        inputs.each { TransformInput input ->
            input.directoryInputs.each { DirectoryInput directoryInput ->
                println "classOutputDir=$classOutputDir;$directoryInput.name"
                loadDirectory(directoryInput.file.absolutePath)
                def dest = outputProvider.getContentLocation(directoryInput.name,
                        directoryInput.contentTypes, directoryInput.scopes,
                        Format.DIRECTORY)
                FileUtils.copyDirectory(directoryInput.file, dest)
            }
            input.jarInputs.each { JarInput jarInput ->
                def jarName = jarInput.name
                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4)
                }
                loadJar(jarInput.file)
                def dest = outputProvider.getContentLocation(jarName + md5Name,
                        jarInput.contentTypes, jarInput.scopes, Format.JAR)
                FileUtils.copyFile(jarInput.file, dest)
            }
        }

        serviceConfig.commit()
    }

    void initServiceConfig(String variantName) {
        serviceConfig = new ServiceConfig(mProject, this)
//        System.out.println("android path=" + project.android.bootClasspath[0].toString())
        mProject.android.applicationVariants.all { variant ->
            if (variant.name.contains(variantName)) {
                sourceDir = variant.getVariantData().getScope().getBuildConfigSourceOutputDir()
                println "project.buildDir=" + mProject.buildDir.toString() + "\\intermediates\\classes\\debug"
//                    variant.getCompileClasspath().each { fileDependency ->
//                        if (fileDependency.path.contains("support-annotations")) {
//                            pool.insertClassPath(fileDependency.path)
//                            println "test " + fileDependency.path
//                        }
//                    }
            }
        }
    }

    void loadJar(File jar) {
        serviceConfig.addClassPath(jar.absolutePath)
        JarFile jarFile = new JarFile(jar)
        Enumeration<JarEntry> entries = jarFile.entries()
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement()
            String fileName = entry.name
            if (isArtifactClass(fileName)) {
                InputStream jarInputStream = jarFile.getInputStream(entry)
                serviceConfig.update(fileName, jarInputStream)
            }
        }
    }

    void loadDirectory(String path) {
        File dir = new File(path)
        serviceConfig.addClassPath(path)
        if (dir.isDirectory()) {
            dir.eachFileRecurse { File file ->
                String filePath = file.absolutePath
                if (isArtifactClass(filePath)) {
                    serviceConfig.update(filePath, new FileInputStream(file))
                }
            }
        } else {
            serviceConfig.update(path, new FileInputStream(dir))
        }
    }

    boolean isArtifactClass(String fileName) {
        return fileName.endsWith(".class") &&
                !fileName.contains('R$') &&
                !fileName.contains('R.class') &&
                !fileName.contains("BuildConfig.class")
    }
}
